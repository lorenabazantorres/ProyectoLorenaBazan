/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import MYSQL.*;
import Negocio.*;
import Objetos.*;
import Objetos.EmpleadosDTO;
import Objetos.OrderDTO;
import Objetos.PrendasDTO;
import Objetos.Tallaje;
import static java.lang.System.in;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LORENA
 */
public class Test {

    static Connection conexion = null;

    static EmpleadosDAO empleadosDAO = new EmpleadosDAOJDBC();
    static ClientesDAO clientesDao = new ClientesDAOJDBC();

    static ICatalogoTienda catalogo = new CatalogoTiendaImp();

    static String order = "CarritoCompra.txt";
    static String articulostienda = "CatalogoPrendas.txt";
    static String ficheroventas = "FicheroVentas.txt";
    static String ficheroordenado = "FicheroOrdenado.txt";

    public static void main(String[] args) throws SQLException, ParseException {
        //CONEXIONES

        conexion = Conexion.getConnection();

        if (conexion.getAutoCommit()) {
            conexion.setAutoCommit(false);
        }

        ClientesDAOJDBC clientesDao = new ClientesDAOJDBC(conexion);
        EmpleadosDAOJDBC empleadosDao = new EmpleadosDAOJDBC(conexion);

        menuinicial();
    }

//MENU INICIAL : INICIAR SESION COMO EMPLEADO O REGISTRAR UNO NUEVO
//------------------------------------------------------------------
    public static void menuinicial() throws SQLException, ParseException {
        int opcion;
        Scanner menu = new Scanner(System.in);
        Scanner ent = new Scanner(System.in);
        while (true) {
            System.out.println("\n------------BIENVENIDO A LA APLICACION------------");
            System.out.println("----SELECCIONES UNA DE LAS SIGUIENTES OPCIONES----\n");

            System.out.println("1.- Iniciar sesion como empleado");
            System.out.println("2.- Registrarse como nuevo empleado");

            System.out.println("0.- Salir de la aplicación");
            System.out.println("Seleccione una de las opciones");

            opcion = menu.nextInt();
            switch (opcion) {
                case 1:
                    try {
                    System.out.println("Escriba su dni para poder inciar sesion como empleado...");
                    System.out.println("DNI: ");
                    String dnibuscar = ent.nextLine();

                    EmpleadosDTO empleadotmp = empleadosDAO.buscarEmpleado(dnibuscar);

                    if (empleadotmp != null) {
                        menugestion(empleadotmp);
                    } else {
                        System.out.println("Usuario no registrado, debe crear una cuenta para poder comprar");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
                case 2:
                    //Crear nuevo empleado
                    System.out.println("Introduzca sus datos para crear un nuevo usuario...");

                    System.out.println("Introduce tu dni: ");
                    String dni = ent.nextLine();

                    System.out.println("Introduce tu nombre: ");
                    String nombre = ent.nextLine();

                    System.out.println("Introduce tus apellidos: ");
                    String apellidos = ent.nextLine();

                    empleadosDAO.insertar(new EmpleadosDTO(dni, nombre, apellidos));

                    break;

                case 0:
                    System.out.println("Gracias por utilizar la aplicación");
                    break;
                default:
                    System.out.println("Debe seleccionar una opción entre 0 y 2");
            }
        }
    }

    //MENU GESTION / VENTAS
    public static void menugestion(EmpleadosDTO empleadotmp) throws SQLException, ParseException {
        int opcion;
        Scanner menu = new Scanner(System.in);
        Scanner ent = new Scanner(System.in);
        while (true) {
            System.out.println("\n---------------MENU GESTION / VENTAS--------------");
            System.out.println("----SELECCIONES UNA DE LAS SIGUIENTES OPCIONES----\n");

            System.out.println("1.- Realizar ventas");
            System.out.println("2.- Gestionar datos de la tienda");

            System.out.println("0.- Salir de la aplicación");
            System.out.println("Seleccione una de las opciones");

            opcion = menu.nextInt();
            switch (opcion) {
                case 1:
                    menucliente(empleadotmp);
                    break;
                case 2:
                    menuempleado(empleadotmp);
                    break;
                case 0:
                    menuinicial();
                    break;
                default:
                    System.out.println("Debe seleccionar una opción entre 0 y 2");
            }
        }
    }

    //MENU REGISTRO EMPLEADO
    //----------------------
    public static void menuempleado(EmpleadosDTO empleadotmp) throws SQLException, ParseException {
        int opcion;
        Scanner menu = new Scanner(System.in);
        Scanner ent = new Scanner(System.in);
        Scanner entprenda = new Scanner(System.in);
        PrendasDTO prendaEncontrada = null;
        while (true) {
            System.out.println("\n---------------MENU GESTION DE TIENDA--------------");
            System.out.println("----SELECCIONES UNA DE LAS SIGUIENTES OPCIONES----\n");

            System.out.println("1.- Listar los empleados de la tienda");
            System.out.println("2.- Borrar empleado de la base de datos");
            System.out.println("3.- Ordenar empleados descendentemente por nombre\n");

            System.out.println("4.- Listar los clientes de la tienda");
            System.out.println("5.- Ordenar clientes ascendendentemente por apellido\n");

            System.out.println("6.- Iniciar catalogo de articulos");
            System.out.println("7.- Añadir articulos al catalogo");
            System.out.println("8.- Listar los articulos del catalogo");
            System.out.println("9.- Buscar articulo del catalogo");
            System.out.println("10.- Ordenar catalogo ascendentemente por referencia\n");

            System.out.println("11.- Iniciar el fichero ventas\n");

            System.out.println("0.- Salir de la aplicación");
            System.out.println("Seleccione una de las opciones");

            opcion = menu.nextInt();
            switch (opcion) {
                case 1:
                    //LISTAR EMPLEADOS
                    System.out.println("\n--LISTADO DE LOS EMPLEADOS REGISTRADOS--\n");
                    System.out.println("   DNI\t\tNOMBRE\t\t   APELLIDOS");
                    System.out.println("---------\t------\t\t-------------");
                    try {
                        List<EmpleadosDTO> empl = empleadosDAO.seleccionar();
                        empl.forEach(empleado -> {
                            System.out.println(empleado);
                        });
                        System.out.println("\n");
                    } catch (SQLException ex) {
                        Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case 2:
                    //BORRAR EMPLEADO
                    System.out.println("\nIntroduzca el DNI de empleado que quiere borrar...");

                    System.out.println("DNI: ");
                    String dniborrar = ent.nextLine();

                    try {
                        empleadosDAO.borrarporId(new EmpleadosDTO(dniborrar));
                        System.out.println("\n");
                    } catch (SQLException ex) {
                        Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case 3:
                    //ORDENAR EMPLEADOS DESCENDENTEMENTE POR NOMBRE
                    System.out.println("\n--LISTADO EN ORDEN DESCENDENTE DE LOS EMPLEADOS--\n");
                    System.out.println("   DNI\t\tNOMBRE\t\t   APELLIDOS");
                    System.out.println("---------\t------\t\t-------------");
                    List<EmpleadosDTO> empl = empleadosDAO.OrdenarDescNombre();
                    empl.forEach(empleado -> {
                        System.out.println(empleado);
                    });
                    System.out.println("\n");
                    break;

                case 4:
                    //LISTADO DE CLIENTES
                    System.out.println("\n--LISTADO DE LOS CLIENTES REGISTRADOS--\n");
                    System.out.println("   DNI\t\tNOMBRE\t\t  APELLIDOS\t\tCORREO ELECTRONICO\tTELEFONO\t\t  USUARIO\tCONTRASENA");
                    System.out.println("---------\t----------\t---------------\t\t------------------\t---------\t-----------\t-----------");
                    try {
                        List<ClientesDTO> cl = clientesDao.listar();
                        cl.forEach(clientes -> {
                            System.out.println(clientes);
                        });
                        System.out.println("\n");
                    } catch (SQLException ex) {
                        Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case 5:
                    //ORDENAR LISTADO CLIENTES ASCENDENTE
                    System.out.println("\n--LISTADO DE LOS CLIENTES REGISTRADOS EN ORDEN ASCENDENTE POR APELLIDOS--\n");
                    System.out.println("   DNI\t\tNOMBRE\t\t  APELLIDOS\t\tCORREO ELECTRONICO\tTELEFONO\t\t  USUARIO\tCONTRASENA");
                    System.out.println("---------\t----------\t---------------\t\t------------------\t---------\t-----------\t-----------");

                    List<ClientesDTO> cl = clientesDao.OdenarApellidos();
                    cl.forEach(clientes -> {
                        System.out.println(clientes);
                    });
                    System.out.println("\n");
                    break;

                case 6:
                    //INICIAR CATALOGO DE ARTICULOS
                    catalogo.iniciar(articulostienda);
                    break;

                case 7:
                    //AÑADIR ARTICULOS AL CATALOGO
                    System.out.println("\nIntroduzca los siguientes datos para agregar un nuevo producto al catálogo...");

                    System.out.println("- Escriba la referencia de la prenda: ");
                    String referencia = entprenda.nextLine();

                    System.out.println("- Escriba el nombre de la prenda: ");
                    String nombrearticulo = entprenda.nextLine();

                    System.out.println("- Escriba el precio de la prenda: ");
                    double precio = entprenda.nextDouble();
                    entprenda.nextLine();

                    System.out.println("- Escriba una breve descripcion del articulo: ");
                    String descripcion = entprenda.nextLine();

                    System.out.println("- Escriba la fecha de temporada de la prenda (dd/mm/yyyy): ");
                    String fechaString = entprenda.nextLine();
                    SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                    Date fecha = df.parse(fechaString);

                    System.out.println("- Seleccione la talla: ");
                    Tallaje talla = menutalla();

                    catalogo.agregarPrendaCatalogo(new PrendasDTO(referencia, nombrearticulo, precio, descripcion, fecha, talla), articulostienda);
                    System.out.println("\n");
                    break;

                case 8:
                    //LISTAR ARTICULOS DEL CATALOGO
                    System.out.println("\n--LISTADO DE TODOS LOS ARTICULOS DE LA TIENDA--");
                    System.out.println("------------------------------------------------\n");

                    catalogo.listarRecurso(articulostienda);
                    System.out.println("\n ");
                    break;

                case 9:
                    //BUSCAR ARTICULO
                    System.out.println("\nIntroduce la referencia de la prenda que quiere buscar: ");
                    String refprod = entprenda.nextLine();
                    prendaEncontrada = catalogo.buscarPrenda(articulostienda, refprod);
                    if (prendaEncontrada != null) {
                        System.out.println("La prenda ha sido encontrada");
                        System.out.println("- Referencia : " + prendaEncontrada.getReferencia());
                        System.out.println("- Nombre : " + prendaEncontrada.getNombre());
                        System.out.println("- Precio : " + prendaEncontrada.getPrecio());
                        System.out.println("- Descripcion : " + prendaEncontrada.getDescripcion());
                        System.out.println("- Fecha de temporada : " + prendaEncontrada.getfecha_temporada_toString());
                        System.out.println("- Talla : " + prendaEncontrada.getTalla());
                    } else {
                        System.out.println("La prenda no se encuentra en el catálogo de ropa");
                    }
                    System.out.println("\n");
                    break;

                case 10:
                    //ORDENAR FICHERO ARTICULOS
                    catalogo.iniciar(ficheroordenado);
                    catalogo.ordenarfichero(articulostienda, ficheroordenado);
                    System.out.println("\n");
                    break;

                case 11:
                    //INICIAR FICHERO VENTAS
                    catalogo.iniciar(ficheroventas);
                    break;
                    
                case 0:
                    menugestion(empleadotmp);
                    break;
                default:
                    System.out.println("Debe seleccionar una opción entre 0 y 12");
            }
        }

    }

    //MENU REGISTRO CLIENTE
    //----------------------
    public static void menucliente(EmpleadosDTO empleadotmp) throws SQLException, ParseException {
        int opcion;
        String contrasena = "";
        String usuario = "";
        Scanner ent = new Scanner(System.in);
        Scanner menu = new Scanner(System.in);
        while (true) {
            System.out.println("\n------------------MENU CLIENTE--------------------");
            System.out.println("----SELECCIONES UNA DE LAS SIGUIENTES OPCIONES----\n");

            System.out.println("1.- Registrar un nuevo cliente");
            System.out.println("2.- Dar de baja a un cliente");
            System.out.println("3.- Iniciar sesión para comprar");

            System.out.println("0.- Salir de la aplicación");
            System.out.println("Seleccione una de las opciones");

            opcion = menu.nextInt();
            switch (opcion) {
                case 1:
                    //Crear nuevo cliente
                    System.out.println("Introduzca sus datos para crear un nuevo usuario...");

                    System.out.println("Introduce tu dni: ");
                    String dni = ent.nextLine();

                    System.out.println("Introduce tu nombre: ");
                    String nombre = ent.nextLine();

                    System.out.println("Introduce tus apellidos: ");
                    String apellidos = ent.nextLine();

                    System.out.println("Introduce tu email: ");
                    String email = ent.nextLine();

                    System.out.println("Introduce tu telefono: ");
                    String telefono = ent.nextLine();

                    System.out.println("Introduce tu usuario: ");
                    usuario = ent.nextLine();

                    System.out.println("Introduce tu contraseña: ");
                    contrasena = ent.nextLine();

                    clientesDao.insertar(new ClientesDTO(dni, nombre, apellidos, email, telefono, usuario, contrasena));

                    break;

                case 2:
                    System.out.println("Para darse de baja en la aplicación, introduzca su DNI...");

                    System.out.println("DNI: ");
                    String dniborrar = ent.nextLine();

                    clientesDao.borrarporId(new ClientesDTO(dniborrar));

                    break;

                case 3:
                   try {
                    System.out.println("Escriba su nombre de usuario y contraseña para poder inciar sesion...");
                    System.out.println("USUARIO: ");
                    String usuariobuscar = ent.nextLine();

                    System.out.println("CONTRASEÑA: ");
                    String contrasenabuscar = ent.nextLine();

                    ClientesDTO clientetmp = clientesDao.buscarUsuario(usuariobuscar, contrasenabuscar);

                    if (clientetmp != null) {
                        menucompra(clientetmp, empleadotmp);
                    } else {
                        System.out.println("Usuario no registrado, debe crear una cuenta para poder comprar");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

                case 0:
                    menugestion(empleadotmp);
                    break;
                default:
                    System.out.println("Debe seleccionar una opción entre 0 y 3");
            }
        }
    }

    //MENU COMPRA
    //------------
    public static void menucompra(ClientesDTO clientetmp, EmpleadosDTO empleado) throws SQLException, ParseException {
        String opcion;
        Integer cantidadtotal = 0;
        Scanner ent = new Scanner(System.in);
        Scanner menu = new Scanner(System.in);
        PrendasDTO refEncontrada = null;
        boolean repetir = true;
        while (repetir) {
            System.out.println("\n------------------MENU VENTA--------------------");
            System.out.println("----SELECCIONES UNA DE LAS SIGUIENTES OPCIONES----\n");
            System.out.println("1.- Inicializar fichero");
            System.out.println("2.- Añadir articulos al carrito");
            System.out.println("3.- Editar datos del cliente");
            System.out.println("0.- Salir de la aplicación");
            System.out.println("Seleccione una de las opciones");

            opcion = menu.nextLine();

            switch (opcion) {
                case "1":
                    catalogo.iniciar(order);
                    break;

                case "2":
                    System.out.println("\n--------CATALOGO DE ROPA DE LA TIENDA--------- ");
                    System.out.println("Escriba la palabra 'FIN' para terminar la compra\n");

                    catalogo.listarRecurso(articulostienda);
                    boolean repetircompra = true;

                    List<OrderDTO> arrayArticulosCompra = new ArrayList<>();
                    Date fecha_compra = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                    EmpleadosDTO empleadotmp = new EmpleadosDTO(empleado.getDni());

                    while (repetircompra) {
                        String referencia;

                        System.out.println("\t\t--------------------");

                        System.out.println("\nEscriba la referencia del producto que desea agregar a su carrito: ");
                        referencia = ent.nextLine();

                        if (referencia.equalsIgnoreCase("FIN")) {
                            repetircompra = false;
                            System.out.println("\nREF\tARTICULO\t\tPRECIO\tDESCRIPCION\tTEMPORADA\tTALLA");
                            System.out.println("---\t--------\t\t------\t-----------\t---------\t-----");

                            for (OrderDTO n_compra : arrayArticulosCompra) {
                                catalogo.agregarCompra(n_compra, ficheroventas);

                                System.out.println(n_compra.getRef().toString());
                            }

                            double total = catalogo.calcularTotalPrecio(arrayArticulosCompra);
                            System.out.println("\nEl precio total es: " + total + " € ");

                            double max = catalogo.maxPrecioArticulo(arrayArticulosCompra);
                            System.out.println("El articulo mas caro ha costado " + max + " € ");

                            int cont = catalogo.contadorArticulos(arrayArticulosCompra);

                            System.out.println("Ha comprado un total de " + cont + " artículos");

                            catalogo.iniciar(order);

                        } else {

                            System.out.println("Indique la cantidad de artículos con referencia " + referencia);
                            Integer cantidad = ent.nextInt();
                            ent.nextLine();

                            refEncontrada = catalogo.comprarPrenda(articulostienda, referencia);

                            catalogo.agregarPrendaCarrito(refEncontrada, order);

                            if (refEncontrada != null) {
                                System.out.println("-> Se ha agregado a su carrito " + cantidad + " unidades del producto " + refEncontrada.getNombre());

                                OrderDTO compratmp = new OrderDTO(fecha_compra, cantidad, clientetmp, refEncontrada, empleadotmp);
                                arrayArticulosCompra.add(compratmp);

                            } else {
                                System.out.println("Prenda no encontrada, no ha sido posible agregarla al carrito");
                            }
                        }
                    }
                    break;

                case "3":
                    System.out.println("Introduzca sus datos para modificar su usuario...");

                    System.out.println("Nombre actual: " + clientetmp.getNombre() + " | Introduce tu nombre: ");
                    String nombre = ent.nextLine();

                    System.out.println("Apellidos actuales: " + clientetmp.getApellidos() + " | Introduce tus apellidos: ");
                    String apellidos = ent.nextLine();

                    System.out.println("Email actual: " + clientetmp.getEmail() + " | Introduce tu email: ");
                    String email = ent.nextLine();

                    System.out.println("Telefono actual: " + clientetmp.getTelefono() + " | Introduce tu telefono: ");
                    String telefono = ent.nextLine();

                    System.out.println("Nombre de usuario actual: " + clientetmp.getUsuario() + " | Introduce tu usuario: ");
                    String usuario = ent.nextLine();

                    System.out.println("Contraseña actual: " + clientetmp.getContrasena() + " | Introduce tu contraseña: ");
                    String contrasena = ent.nextLine();

                    clientesDao.actualizar(new ClientesDTO(clientetmp.getDni(), nombre, apellidos, email, telefono, usuario, contrasena));

                    break;

                case "0":
                    repetir = false;
                    menuinicial();
                    break;

                default:
                    System.out.println("Debe seleccionar una opción entre 0 y 3");
            }
        }

    }

    //MENU ELEGIR TALLA
    public static Tallaje menutalla() {
        int opcion;
        Scanner menu = new Scanner(System.in);
        Scanner lectura = new Scanner(System.in);
        Scanner sn = new Scanner(System.in);
        String nombrePrenda = "";

        Tallaje talla = null;
        while (true) {
            System.out.println("------------------\n");
            System.out.println("1.- XXS");
            System.out.println("2.- XS");
            System.out.println("3.- S");
            System.out.println("4.- M");
            System.out.println("5.- L");
            System.out.println("6.- XL");
            System.out.println("7.- XXL");
            System.out.println("0.- Salir");
            opcion = menu.nextInt();
            switch (opcion) {
                case 1:
                    return Tallaje.XXS;
                case 2:
                    return Tallaje.XS;
                case 3:
                    return Tallaje.S;
                case 4:
                    return Tallaje.M;
                case 5:
                    return Tallaje.L;
                case 6:
                    return Tallaje.XL;
                case 7:
                    return Tallaje.XXL;

            }

        }

    }

}
