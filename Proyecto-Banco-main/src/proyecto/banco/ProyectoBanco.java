package proyecto.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Date;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ProyectoBanco {

    public static void main(String[] args) throws SQLException, InterruptedException {
        Scanner num = new Scanner(System.in);
        Scanner text = new Scanner(System.in);

        ArrayList<Cuenta> cuentas = new ArrayList<>();
        poblarArray(cuentas);
        poblarArrayAhorro(cuentas);
        //Esto solo es para que sea más cómodo usar el programa, se puede borrar
        for (int e = 0; e < cuentas.size(); e++) {
            System.out.println(cuentas.get(e) + "\n");
        }
        System.out.println("	\u001B[33m (Las cuentas mostradas aquí únicamente son para facilitar la demostración)");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        cartel();
        System.out.println("LA APLICACIÓN ESTÁ PENSADA PARA TENER LA CONSOLA AMPLIADA AL MÁXIMO\u001B[37m");
        
        int opc1;
        int opc2;
        int opc3;
        int opc4;

        do {
            menuPrincipal();
            opc1 = num.nextInt();

            switch (opc1) {
                //CREAR CUENTA
                case 1: {
                    do {
                        menuCrearCuenta();
                        opc2 = num.nextInt();

                        switch (opc2) {
                            //CREAR CUENTA CORRIENTE
                            case 1: {
                                System.out.println("Introduzca su nombre.");
                                String nombre = text.nextLine();
                                System.out.println("Introduzca sus apellidos.");
                                String apellidos = text.nextLine();
                                System.out.println("Ingrese su saldo");
                                double saldo = num.nextDouble();
                                cuentas.add(new Cuenta(nombre, apellidos, saldo, cuentas));
                                System.out.println(cuentas.get(cuentas.size() - 1).toString());
                                guardarNuevaCuentaC(nombre, apellidos, saldo, cuentas);

                                break;
                            }

                            //CREAR CUENTA AHORRO
                            case 2: {
                                System.out.println("Introduzca su nombre.");
                                String nombre = text.nextLine();
                                System.out.println("Introduzca sus apellidos.");
                                String apellidos = text.nextLine();
                                System.out.println("Ingrese su saldo");
                                double saldo = num.nextDouble();
                                cuentas.add(new CuentaAhorro(nombre, apellidos, saldo, cuentas));
                                System.out.println(cuentas.get(cuentas.size() - 1).toString());
                                guardarNuevaCuentaA(nombre, apellidos, saldo, cuentas);

                                break;
                            }
                            // Salir del bucle
                            case 3: {
                                break;
                            }
                            default:
                                System.out.println("Elija una opción válida");
                                break;
                        }
                    } while (opc2 != 3);

                    break;
                }
                //ACCEDER A CUENTA
                case 2: {
                    int i = 0;
                    boolean encontrado = false;
                    System.out.println("Introduzca el número de la cuenta: ");
                    String miNumC = text.nextLine().toUpperCase();

                    while (i < cuentas.size() && !encontrado) {
                        Cuenta a = cuentas.get(i);
                        if (miNumC.equals(a.getNumCuenta())) {
                            encontrado = true;
                            if (cuentas.get(i) instanceof CuentaAhorro) {

                                boolean salir = false;
                                while (!salir) {
                                    menuOpcionesCuentaAhorro();

                                    opc3 = num.nextInt();
                                    switch (opc3) {
                                        //Mostrar datos.
                                        case 1: {
                                            System.out.println(cuentas.get(i).toString());
                                            break;
                                        }
                                        //Mostrar saldo.
                                        case 2: {
                                            cuentas.get(i).calcularInteres();
                                            String tipoCuenta = "CuentaAhorro";
                                            actualizarFecha(cuentas, i, tipoCuenta);
                                            System.out.println("Su saldo es: \u001B[32m" + cuentas.get(i).getSaldo() + "€\u001B[37m");
                                            break;
                                        }
                                        //Ingresar dinero.
                                        case 3: {
                                            double ingreso;
                                            System.out.println("¿Qué cantidad de dinero desea ingresar?");
                                            ingreso = num.nextDouble();
                                            cuentas.get(i).ingresar(ingreso);
                                            String tipoCuenta = "CuentaAhorro";
                                            actualizarCuenta(cuentas, i, tipoCuenta);
                                            System.out.println("Se ha ingresado la cantidad: \u001B[32m" + ingreso + "€ de la cuenta.\nSu saldo actual es de " + cuentas.get(i).getSaldo() + "€\u001B[37m");

                                            break;
                                        }
                                        case 4: {
                                            esAlejandro(cuentas,i);
                                            System.out.println("\u001B[31m¿Estás seguro de que quieres borrar la cuenta? S/N\u001B[37m");
                                            //boolean borrado = false;
                                            String borrado = text.nextLine().toLowerCase();
                                            if (borrado.equals("s") || borrado.equals("si")) {
                                                //método borrar cuenta 
                                                String cuentaA = "CuentaAhorro";
                                                borrarCuenta(cuentas, i, cuentaA);
                                                //método sacar cuenta de array
                                                cuentas.remove(i);
                                                System.out.println("Cuenta borrada.\u001B[37m");
                                                salir = true;
                                                break;
                                            }
                                            break;
                                        }
                                        case 5: {
                                            salir = true;
                                            break;
                                        }
                                        default:
                                            System.out.println("Elija una opción válida");
                                            break;
                                    }
                                }
                            } else {
                                boolean salir = false;
                                while (!salir) {
                                    menuOpciones();
                                    opc4 = num.nextInt();
                                    switch (opc4) {
                                        //Mostrar datos.
                                        case 1: {
                                            System.out.println(cuentas.get(i).toString());
                                            break;
                                        }
                                        //Mostrar saldo.
                                        case 2: {
                                            System.out.println("Su saldo es:  \u001B[32m" + cuentas.get(i).getSaldo() + "€\u001B[37m");
                                            break;
                                        }
                                        //Ingresar dinero.
                                        case 3: {
                                            double ingreso;
                                            System.out.println("¿Qué cantidad de dinero desea ingresar?");
                                            ingreso = num.nextDouble();
                                            
                                            cuentas.get(i).ingresar(ingreso);
                                            String cuentaC = "Cuenta";
                                            actualizarCuenta(cuentas, i, cuentaC);
                                            System.out.println("Se ha ingresado la cantidad:  \u001B[32m" + ingreso + "€\u001B[37m\nSu saldo actual es de:  \u001B[32m" + cuentas.get(i).getSaldo() + "€\u001B[37m");
                                            break;
                                        }
                                        //Retirar dinero.
                                        case 4: {
                                            double retiro;
                                            System.out.println("¿Qué cantidad de dinero desea retirar?");
                                            retiro = num.nextDouble();
                                            cuentas.get(i).retirar(retiro);
                                            String cuentaC = "Cuenta";
                                            actualizarCuenta(cuentas, i, cuentaC);
                                            System.out.println("Se ha retirado la cantidad \u001B[31m" + retiro + "€\u001B[37m de la cuenta.\nSu saldo actual es de:  \u001B[32m" + cuentas.get(i).getSaldo() + "€\u001B[37m");
                                            break;
                                        }
                                        //Realizar Transferencia.
                                        case 5: {
                                            int j = 0;
                                            boolean encuentra = false;
                                            System.out.println("Introduzca el nombre de la cuenta a la que desea hacer la transferencia: ");
                                            String numCuentaDestino = text.nextLine().toUpperCase();
                                            Cuenta destino;

                                            // Comprueba si el número de cuenta destino coincide con el de la cuenta actual
                                            if (numCuentaDestino.equals(cuentas.get(i).getNumCuenta())) {
                                                System.out.println("No puede realizar transferencias a su propia cuenta.");
                                                encuentra = true;
                                            } else {
                                                while (j < cuentas.size() && !encuentra) {
                                                    destino = cuentas.get(j);
                                                    if (numCuentaDestino.equals(destino.getNumCuenta())) {
                                                        System.out.println("¿Qué cantidad de dinero desea transferir?");
                                                        double monto = num.nextDouble();

                                                        // Hace la transferencia
                                                        cuentas.get(i).transferencia(i, j, monto, cuentas);
                                                        String cuentaC = "Cuenta";
                                                        actualizarCuenta(cuentas, i, cuentaC);

                                                        //Tengo que comprobar si es cuenta corriente o de ahorro para poder actualizar una tabla u otra
                                                        if (cuentas.get(j) instanceof CuentaAhorro) {
                                                            String cuentaA = "CuentaAhorro";
                                                            actualizarCuenta(cuentas, j, cuentaA);
                                                            encuentra = true;
                                                        } else {
                                                            actualizarCuenta(cuentas, j, cuentaC);
                                                            encuentra = true;
                                                        }
                                                        System.out.println("Se ha transferido la cantidad \u001B[32m"+ monto + "€\u001B[37m a la cuenta :" + numCuentaDestino);
                                                    }
                                                    j++;
                                                }
                                            }

                                            if (!encuentra) {
                                                System.out.println("La cuenta no existe");
                                            }
                                            break;
                                        }

                                        case 6: {
                                            esAlejandro(cuentas,i);
                                            System.out.println("\u001B[31m¿Estás seguro de que quieres borrar la cuenta? S/N\u001B[37m");
                                            String borrado = text.nextLine().toLowerCase();
                                            if (borrado.equals("s") || borrado.equals("si")) {
                                                //método borrar cuenta 
                                                String cuentaC = "Cuenta";
                                                borrarCuenta(cuentas, i, cuentaC);
                                                //método sacar cuenta de array
                                                cuentas.remove(i);
                                                System.out.println("Cuenta borrada.");
                                                salir = true;
                                                break;
                                            }
                                            break;
                                        }
                                        case 7: {
                                            salir = true;
                                            break;
                                        }

                                        default:
                                            System.out.println("Elija una opción válida");
                                            break;
                                    }
                                }
                            }
                        }
                        i++;
                    }
                    break;
                }
                case 3: {
                    break;
                }
                default:
                    System.out.println("Elija una opción válida");
                    break;
            }
        } while (opc1 != 3);
        despedida();
    }

    public static void menuPrincipal() {
        System.out.println("");
        System.out.println("****************************************************");
        System.out.println("Seleccione una opción."
                + "\n1-Crear cuenta."
                + "\n2-Acceder a cuenta."
                + "\n3-Salir");
        System.out.println("****************************************************");
    }

    public static void menuCrearCuenta() {
        System.out.println("");
        System.out.println("****************************************************");
        System.out.println("Seleccione una opción."
                + "\n1-Crear cuenta corriente."
                + "\n2-Crear cuenta de ahorro."
                + "\n3-Atrás");
        System.out.println("****************************************************");
    }

    public static void menuOpciones() {
        System.out.println("");
        System.out.println("****************************************************");
        System.out.println("Seleccione una opción."
                + "\n1-Mostrar datos."
                + "\n2-Mostrar saldo."
                + "\n3-Ingresar dinero."
                + "\n4-Retirar dinero."
                + "\n5-Realizar Transferencia."
                + "\n6-Borrar cuenta."
                + "\n7-Atrás");
        System.out.println("****************************************************");
    }

    public static void menuOpcionesCuentaAhorro() {
        System.out.println("");
        System.out.println("****************************************************");
        System.out.println("Seleccione una opción."
                + "\n1-Mostrar datos."
                + "\n2-Mostrar saldo."
                + "\n3-Ingresar dinero."
                + "\n4-Borrar cuenta."
                + "\n5-Atrás");
        System.out.println("****************************************************");
    }

    //METODO PARA METER CUENTAS CORRIENTES EN ARRAY
    public static void poblarArray(ArrayList<Cuenta> cuentas) {

        try {
            String url = "jdbc:sqlserver://localhost;encrypt=true; databaseName=ProyectoBanco;integratedSecurity=true;trustServerCertificate=true";

            Connection con = DriverManager.getConnection(url);

            Statement statement = con.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT nombre, apellidos, numCuenta, saldo FROM Cuenta");

            while (resultset.next()) {
                String nombre = resultset.getString("nombre");
                String apellidos = resultset.getString("apellidos");
                String cuentaId = resultset.getString("numCuenta");
                Double dinero = resultset.getDouble("saldo");
                // System.out.printf("\033[0;1m \u001B[36m %s %s %s %f\n", nombre, apellidos,cuentaId, dinero);
                cuentas.add(new Cuenta(nombre, apellidos, dinero, cuentaId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //METODO PARA METER CUENTAS DE AHORRO EN ARRAY
    public static void poblarArrayAhorro(ArrayList<Cuenta> cuentas) {

        try {
            String url = "jdbc:sqlserver://localhost;encrypt=true; databaseName=ProyectoBanco;integratedSecurity=true;trustServerCertificate=true";

            Connection con = DriverManager.getConnection(url);

            Statement statement = con.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT nombre, apellidos, numCuenta, saldo, interes, fechaCreacion, accesoAux FROM CuentaAhorro");

            while (resultset.next()) {
                String nombre = resultset.getString("nombre");
                String apellidos = resultset.getString("apellidos");
                String cuentaId = resultset.getString("numCuenta");
                Double dinero = resultset.getDouble("saldo");
                Double interes = resultset.getDouble("interes");
                LocalDate fechaCreacion = resultset.getDate("fechaCreacion").toLocalDate();
                LocalDate accesoAux = resultset.getDate("accesoAux").toLocalDate();
                //System.out.printf("\033[0;1m \u001B[36m %s %s %s %f\n", nombre, apellidos,cuentaId, dinero, interes, fechaCreacion);
                cuentas.add(new CuentaAhorro(nombre, apellidos, dinero, cuentaId, interes, fechaCreacion, accesoAux));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Método para meter cuentas corrientes en la base de datos
    public static void guardarNuevaCuentaC(String nombre, String apellidos, double saldo, ArrayList<Cuenta> cuentas) throws SQLException {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost;encrypt=true; databaseName=ProyectoBanco;integratedSecurity=true;trustServerCertificate=true");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Cuenta(nombre, apellidos,numCuenta,saldo)" + " VALUES(?,?,?,?)");

            con.setAutoCommit(false);

            stmt.setString(1, nombre);
            stmt.setString(2, apellidos);
            String numCuenta = cuentas.get(cuentas.size() - 1).getNumCuenta();
            stmt.setString(3, numCuenta);
            stmt.setDouble(4, saldo);
            stmt.executeUpdate();

            con.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            con.rollback();
        } finally {
            con.close();
        }
    }

    //Método para meter cuentas de ahorro en la base de datos
    public static void guardarNuevaCuentaA(String nombre, String apellidos, double saldo, ArrayList<Cuenta> cuentas) throws SQLException {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost;encrypt=true; databaseName=ProyectoBanco;integratedSecurity=true;trustServerCertificate=true");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO CuentaAhorro(nombre, apellidos,numCuenta,saldo,interes,fechaCreacion,accesoAux)" + " VALUES(?,?,?,?,4,?,?)");

            con.setAutoCommit(false);

            stmt.setString(1, nombre);
            stmt.setString(2, apellidos);
            String numCuenta = cuentas.get(cuentas.size() - 1).getNumCuenta();
            stmt.setString(3, numCuenta);
            stmt.setDouble(4, saldo);
            //Esto me ha dado guerra por los tipos de dato
            LocalDate fechaCreacion = cuentas.get(cuentas.size() - 1).getFechaCreacion();
            Date fecha = Date.valueOf(fechaCreacion);
            stmt.setDate(5, fecha);
            stmt.setDate(6, fecha);
            stmt.executeUpdate();

            con.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            con.rollback();
        } finally {
            con.close();
        }
    }

    //METODO PARA ACTUALIZAR TABLA BD CORRIENTE
    public static void actualizarCuenta(ArrayList<Cuenta> cuentas, int i, String tipoCuenta) throws SQLException {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost;encrypt=true; databaseName=ProyectoBanco;integratedSecurity=true;trustServerCertificate=true");
            PreparedStatement stmt = con.prepareStatement("UPDATE " + tipoCuenta + " SET saldo =? WHERE numCuenta =?");

            con.setAutoCommit(false);
            double ingreso = cuentas.get(i).getSaldo();
            stmt.setDouble(1, ingreso);
            String numCuenta = cuentas.get(i).getNumCuenta();
            stmt.setString(2, numCuenta);
            stmt.executeUpdate();

            con.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            con.rollback();
        } finally {
            con.close();
        }
    }
    
        public static void actualizarFecha(ArrayList<Cuenta> cuentas, int i, String tipoCuenta) throws SQLException {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost;encrypt=true; databaseName=ProyectoBanco;integratedSecurity=true;trustServerCertificate=true");
            PreparedStatement stmt = con.prepareStatement("UPDATE " + tipoCuenta + " SET saldo =?, accesoAux =? WHERE numCuenta =?");

            con.setAutoCommit(false);
            double ingreso = cuentas.get(i).getSaldo();
            stmt.setDouble(1, ingreso);
            
            LocalDate fechaAux = cuentas.get(cuentas.size() - 1).getAccesoAux();
            Date fecha = Date.valueOf(fechaAux);
            stmt.setDate(2, fecha);
            
            String numCuenta = cuentas.get(i).getNumCuenta();
            stmt.setString(3, numCuenta);
            stmt.executeUpdate();

            con.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            con.rollback();
        } finally {
            con.close();
        }
    }

    public static void borrarCuenta(ArrayList<Cuenta> cuentas, int i, String tipoCuenta) throws SQLException {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost;encrypt=true; databaseName=ProyectoBanco;integratedSecurity=true;trustServerCertificate=true");
            PreparedStatement stmt = con.prepareStatement("DELETE FROM " + tipoCuenta + " WHERE numCuenta =?");

            con.setAutoCommit(false);
            String numCuenta = cuentas.get(i).getNumCuenta();
            stmt.setString(1, numCuenta);
            stmt.executeUpdate();

            con.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            con.rollback();
        } finally {
            con.close();
        }
    }
    
     public static void cartel(){
         System.out.println("**************************************************************************\n" +
" _____                                                                           _____ \n" +
"( ___ )                                                                         ( ___ )\n" +
" |   |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|   | \n" +
" |   |  .d8888b.        d8888 888b     d888        d8888 8888888b.         d8888 |   | \n" +
" |   | d88P  Y88b      d88888 8888b   d8888       d88888 888   Y88b       d88888 |   | \n" +
" |   | 888    888     d88P888 88888b.d88888      d88P888 888    888      d88P888 |   | \n" +
" |   | 888           d88P 888 888Y88888P888     d88P 888 888   d88P     d88P 888 |   | \n" +
" |   | 888          d88P  888 888 Y888P 888    d88P  888 8888888P\"     d88P  888 |   | \n" +
" |   | 888    888  d88P   888 888  Y8P  888   d88P   888 888 T88b     d88P   888 |   | \n" +
" |   | Y88b  d88P d8888888888 888   \"   888  d8888888888 888  T88b   d8888888888 |   | \n" +
" |   |  \"Y8888P\" d88P     888 888       888 d88P     888 888   T88b d88P     888 |   | \n" +
" |   |                                                                           |   | \n" +
" |   |                                                                           |   | \n" +
" |   |                                                                           |   | \n" +
" |   |             888888b.         d8888 888b    888 888    d8P                 |   | \n" +
" |   |             888  \"88b       d88888 8888b   888 888   d8P                  |   | \n" +
" |   |             888  .88P      d88P888 88888b  888 888  d8P                   |   | \n" +
" |   |             8888888K.     d88P 888 888Y88b 888 888d88K                    |   | \n" +
" |   |             888  \"Y88b   d88P  888 888 Y88b888 8888888b                   |   | \n" +
" |   |             888    888  d88P   888 888  Y88888 888  Y88b                  |   | \n" +
" |   |             888   d88P d8888888888 888   Y8888 888   Y88b                 |   | \n" +
" |   |             8888888P\" d88P     888 888    Y888 888    Y88b                |   | \n" +
" |___|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|___| \n" +
"(_____)                                                                         (_____)");
     }
    
    public static void despedida(){
        System.out.println("<))))))<))))))))))))))))))))))]]))]#%@@@@@]:      :>+ %@@@#[]]]]]]]]]])])))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))<\n" +
")%####%%%%%%#%%##%%%%%%%%%%%%%%@@@@@@)                  :@@@@@@%%%%%%%%%%%%%%%%%%%%%%%%%#%%%%%####%%%%%%%%%%%%#%%%%%%#%%%%%#%%%%%%%%%)\n" +
")%#%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@[       <@@@   :}) <=)     :@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#####%#%%#%%%##%%%%%%%%%%%%%%%%%%%%)\n" +
")%%%%#%%%%%%#%%%%%%%%%%%%%%%@@@*      =::**>%]#@% +-  -]%%}   @@@@@%%%%%%%%%%%%%%%%%%%%%%%%%%#%%%%#%%%%%%%%%%#%%%%##%#%%%%%%##%###%#%)\n" +
"]%%%%%%%%%%%%%%%%#%%%%%%%@@@@%  +[)@%[++=>== +[>>-@]%@): +#*:  =@@%%%%%%%%%%%%%%%#%%%%%%%%%%%%%%#%%%%%%#%%%%%#%%%#%%%%%###%##%%%#%%##)\n" +
"]%%%%%%%%%%%%@%%%%%%%%%%%@@@   : <  :-+--  -:+     *= : +  #@+-  @@@%#%%%%%%%@%%%%%%%%%%%%%%%%#%%%%%%%%%%%%%%%###%%%###%%#%#%%%%%%%%#)\n" +
"]%%%%%%%%%%%%%%%%%%%%%%%%@@   ->>- )   :<> * + *@%+@+>[}##+-]>=- %@@@@@%%%%%%%%%%%%%%%%%##%%%%%%%%%#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%)\n" +
"]%%%@%%%%@%%%@@@%@%%%%%%@@@  )[<>=-) :>- -+* =)-                  ]@@%@@%%@@%%#%%%%%%%%%%%%#%%%#%%%%%%%%%%%%#%%%%%%%%%%%%%%%%%%%%%%%@]\n" +
"[%@%%%%@%%%%%%%%%%%@%%%@@@ - ]>   =     --:        >@@@@@@@@@>#@%:%@@%%%@@@%%@%@%%%%%%%%%%%%%%%%%#%%#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%)\n" +
"[%%%%%%@%%%@@%%%%@%%@%%@@@ - :=-*%@#}%%%@@@@@@@@@@@@@%%#}[#%%< @[::@@@@%%%%@@%%%@%%%%%%%%%%%%%%%%@@%%%%%%#%%%%%%%%%%%%%%%%%%%%#%%%%%%]\n" +
"}%%%%%@%%@%%%%@@@@%@%%%@@@::<+[}}#%#%##@%@@@@@@%%%}%#%%@%#}}}} #@-=@@@@@@%%%%%%%%@@%%%@%%%%%%%%%%%%%%%%%%%%%%%%%%%%@%%%%%@%%%%%%##%%%]\n" +
"[%%@@%%%%@@@@@@@@@@@@@@@@@ *] %@]#[[}}#@@@@%#####%%%%@@@%%#}[#):@+ =@@%%%%@@@%%%%@%@@@%@@@@@%%%@@%%%@@@@@@%%%%%%%%%%%%%%%%%%%%%@%%@%%]\n" +
"}%%%%@%@@@@%%%%%@%%%%%%@@@  >=:+<#}[[][#@%@}[[[[[[]]]}[}[}[[)[%+}@+ @@@@%%%%%%%%@%@@%%%@%%%%%@%%%@@%@%%%%%@%%%%%%%%%%%%%%%%%%%%%%%%%%]\n" +
"}@%%%@@%@%%@@@@%@%@@@@@%@@  >)+>[}[[]]]))[}#[[[[[[[[[}}}}[[[><[[*:)[@@@@@@@@@@@@@%%%%@%%%@%@@@@@%@%%@@@@@@@@%%%%@@@@@@@@@@@@@%%%%%%%%[\n" +
"}%%%%%%%%@@@%%@@%%%%%%%@@@> :%<=[[))))]]}##[}%###%%%%%%@@@@@@@#[#}: +)-=@@%%%%%%@@@@@@@@@@@@%%%%%%%%%%%%%%@@@%@@@%%@%%%%%%%@%@@@@%%%%[\n" +
"}@@@%@@@@@%%@%@@@@@@@@@@@@: )%: ][][<<]#@@@@@@@@@@@@@@@@@*:  +#### <%[+ @@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@%@%@%@@@@@@@%@@@@@%@@%%%%@@@@@}\n" +
"#@%@@@%%@@%%@@@@%@@%@@@@@@@ @] [%][}@@@@@@@@%@@}#}]}]-  +#%@@%}[#@* @%=-@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%@@@%%%@@@%%%%%}\n" +
"#%@@@@%@@@@@%@@%@@@@@@@@@}@:<  %}}@@       :>])*<<+=          <>)}# =@:@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%%[\n" +
"#%%%%@@@@@@@@@@%%@%@@@@@: *)@ -%}#) )@@@%      >%@@#}<@@@@@@@#}[#[#@:+=@@@@@@@@@@@@@@@@@@@@@%%%%%%%%%%@%@%%%%%%%%%%%%%%%%%%%%%%%%%%%%}\n" +
"}@@@@@@%%%@%%@@@@@@@@@@@@ >)@)+#}[[):  -  @@)#%[%@#][@>=--=*)<[%@]]@%+@@@@@@@@@@@@@@@@@@%%%%%%@@@%@@@@@%%@@%@@@@@@@@%%@@@@@@%@%%@@@@%#\n" +
"[%##%%%%%%%%%#%%#%##%%#@@}-=]= <}]]>#%%%@%= =@#]]@}>]@@@@@@##%%#]<]]<*@%#%%#%%%%@%%%%%@@@@@@@@%%@@%%%@%@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@%\n" +
"[%%%%%%##%%#%%%%#%%%%#%%@@@=++[><][}[)]]]}@@@@[<>#@)*]@%@@@@@@@%[))*=+@@@@@%@@@%%%@@@@%++*%@@@%%%%%%%%%%%@%@@@@@@%@@@@@@@@@@%@@@@@@@@%\n" +
"]%@%%%@@@@@%%@%%%@%%@%%%%@@@)=)}]][#}}%@@@@@%]><)@@@}==-+*>}%@@@[))}@@@@@@@@@@@@@@@@@%>[}[}@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n" +
"]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*]]]])}[}@@@#<-+[@@@@@@%@%[@] :=***>*>@@@@@@@@@@@@@@@@@}>@%#]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%\n" +
"*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@++>[])][%[<: @@]-  --==>])]%@@ >))<+[@@@@@@@@@@@@@@@@@%)%}@]@@@@@@@@@@@@@@@@@@@@@@@%%%%%%%%%%%%%%%%%%%>\n" +
":]][][[}}}}}}#}#}#}}}#######%#%%%[)}]])>- =@@@#%@@}]=>@@@#) :]@@)))@@@%%%%%%%%%%%%%%#%><[]>[}#%%%%%@@%%##}}[[]]])))<<<><>>>>>>>>>>>>>:\n" +
":><<<<>>><>>>>>>>*>>>>>>>>>>>>><][)))<**))@#]}}@@@@@@@@<-=[%>#@)<)} -%<********+*+*+*><+*]>*=+><]]][]}[[[])<<<<>>>><<<<<)<)<<<<)<)))] \n" +
" )<<<<<<<<<<<<<<<>><><>>><>>*>>>*<))]][%@]+  = ::-----*)}}#}@@)*<#@  @%[<**<>>>>>><*+-<]]%<]==***>)]][[}#[][[][]][]]][][[]]][][]][][[:\n" +
" ]]]]]]]]]][]]]]]]]]]]]]])][]])]]][[<])[@@[}@@@[[%@@%##]][}%@[<[#@<   =@%#]])]]]]>=-=>[}[}}@@@@%}>)[[[[}}#[[[]]])])))))])))))]]]))))):\n" +
":)<)))))])<<<)))<)))))<<)<))))))))%=%++<<#])>))))>>+<><]%#%%})[%@   -: : %@#])))*=@@@@@@@@@@@%@@%]*)][[}[[[]]))<)]<<)])]])))))))))))):\n" +
":]<>><)<<)))))))<]<)]))))))<)))<<)@ %@]**>}@%}})<}%#@%@@@%%#%#}@   -:=<*-  ]@@@@)%@@@@@%%)=*][[[#@%<)[}}[}[]]))])<)]]])]]]]]]])]]]]))-\n" +
":)]))))))))<<<))<)<<<))))<))<))))#@  @@#][[[%#%@#%%%@##@@%@@%#@  :==-:*>*=-    -#%#]))]]}%@@}#%%%@@@@@@@@@@}][[]])]]))]]])][[}[[[[[[[-\n" +
":]))<>))))))]])]][]]]])))])]]]][%@     @@}<+]@@%%@@@@@@@@@@%%%  -===-:*-==++*-:    @@%#%%}#%%#[]))}#<    }@@@}[[[[[]][[[[[[}[[}}}}}}}-\n" +
":][[][][[[[[]]][[}}[[[[[[}}[}[#@@-  =:   }@%#>=%@@%%%%%%#>*    =====-:+>+<*=*+*+) =@@@@#)]}]*#%%%%%@@  =:   @@%[[[[[[[[[[[[}[[}}}}}}[-\n" +
"-]]]][]]][][[[[[[]][[[[}[[}[}%@@ :+ ===-     )<:            :=======-:*++->*+ ::>*-[+=-=#@@%#}}###%@@@::+=:  }@}[}[[[[[[[[[[[}}}[}[#}-\n" +
"-[}}]]][[}[[}[}}[[[[[[[[[][[%@#  *) =======-       ::  :-==========- ::=:**:-[@>>+=- @@@@#}#@@@@#}}##%) )<+-  @@[[][[}[]][}}[[[}[}[[[-\n" +
"-][])]][[[[[]]]][[]][][]]][@@] :>*> ===============================::)}]>><>)<:+==<* =%%%<>>>=-<%#%%@%@= <]+  >@]]][]]]][[[[[}[}[}}[}-\n" +
"-]]]])]]][]]]][[[[[[[][[#@@@   =+<< =============================== ><<><>>++*=*>>+-    >@@@@@@@%%@@@@@@)  )}: @%[[]][[}[[[[[[][[[[[[-\n" +
"-][]]]]][[]]][[[[]])}#@@@%   +++-+- -========================---==- +<=*>+=*>*++>)> +<>=@@%%%@@@@@[>*)))% <<-+=%@@%}}[[}}}}#}#}####}#=\n" +
"-[[[]]]]][[[[[[[}}}%@@@    :=+===>}  :======================-=>-==:=)<<[<<>+++*+*+= >>= :   =:   )@%]}<}@- ]*-   @@@%}}#}######%###%#+\n" +
"-}}}}}}}####%##%%@@@@   :>*==*=:+)]}> -===========--=----===+---== =)+<+>**)><*<>[ *]>)>* >* @@@@):*#@%@@) )]>*=   @@@@%######%%#####=\n" +
"=%%##%%%%%###%@@@@[    -***=::>[[*+}>::+=----======+========-====- ><>>*++***+-**::>*+*>))*    <<  =]%%}  ] ]*==+=   +@@@##}##}}####%=\n" +
"=%%####%##%@@@@@    -=+[*<<)[%)=*[+=)* :-=>*-=-----====+=++======:-+=+=*<+>+*>)>) >>)*+*+:*=[+  = %<    @@  }**+**=>*  >@@@#}#}#}}}}}=\n" +
"=[}}##}}#@@@#    +-<=-=+::+]=:*>= *: }> -======-=++==-===+=-====+:->)]*-+++*>**) )>>++++*><+][  =*    [[-   [==+**+=+):  @@@#}}}}}}}}=\n" +
"-[}[[[%@@@    -*<]*+==*+>* <=*=>)*)}[=] :=--+=====-:--++=--===+-= =>*-+]>>=>*>> -)>+>]=-++><+[#  :<+}:   -= #*>++-:+-+=*   @@%####}}#=\n" +
"-#%@@@@<   =+:=<+*)*=+>*++-=++-=*<::+))- -<)+=++==*++>=--======== *>]>+*-]>=:= }%<[<=>))>-+*>[}* :-::-====: %=>++>*+*<*<>-  @@@@##}#%=\n" +
"+@@@     :---=*==>-*+->=+:+:-*>+ :>+]]]]  :::--=-=*+=-=-========- ><)>)[>+>>)+=*[ >==<<+>>>)+*]#: -======- =%)>- =+*+=*+++:   #@@#}}}=\n" +
"[[    -->*=+-><+>++])++=)=+[<==)<<}=-+=#= =====++==--===========::<*>+>=**><>-+*<=**>-++=+**+:=}[:   ::   *@<>><])**>=::*)-=*   @@@%#=\n" +
"*  ==+>=*-:+)#}<<=>)<+*=)]+ [)>)<*>)<<=<) :====-=--============= =]>*<<>*<<*-><-><]***]*+=>>>><}%]]%} =%@[- ++++>=*=>* :+<<+=<-  <@@#=\n" +
"+ <:++*+[< +*#) *>><=:+=-=+  **=>**-]+>]#+ -==-=+*+============= ][))]><*][=:)}]>]+-*>*<<*+->[<= -> :%>  :::   :--+)>> ::=*>+>:*:  @@=\n" +
"+ ]= :+<==}] -]=*><> :>**]+=+:+*-+=>=*+>)} -=-==+---===========- *= :::-*+  +****}<[[]))}@[<]*  :+>: : :++:+<>*=*=:+>>+ :**+==+>*=  @+\n" +
"                                                                                                                                     =");
    
    }
    
    public static void esAlejandro(ArrayList<Cuenta> cuentas, int i) throws InterruptedException {
        if (cuentas.get(i).getNombre().toUpperCase().equals("ALEJANDRO") && cuentas.get(i).getApellidos().toUpperCase().equals("SANZ")) {
            System.out.println("SUPLICA QUE NO SE VAYA, QUE ES ALEJANDRO SANZ");
            TimeUnit.SECONDS.sleep(5);
        }

    }

}

/*Introducir el siguiente script en SQL si no se tiene la base de datos creada:
 
CREATE DATABASE ProyectoBanco
USE ProyectoBanco
 
CREATE TABLE Cuenta(
	nombre varchar(30),
	apellidos varchar(50),
	numCuenta varchar(26) PRIMARY KEY,
	saldo DECIMAL(10,2) )
 
	CREATE TABLE CuentaAhorro(
	nombre varchar(30),
	apellidos varchar(50),
	numCuenta varchar(26) PRIMARY KEY,
	saldo DECIMAL(10,2),
	interes DECIMAL(3,2),
	fechaCreacion DATE,
	accesoAux DATE)
 
INSERT INTO Cuenta (nombre, apellidos, numCuenta, saldo)
VALUES ('Cecilia', 'Romero', 'ES111111111111111111111111', 50000);
INSERT INTO Cuenta (nombre, apellidos, numCuenta, saldo)
VALUES ('Jose', 'Serrano', 'ES222222222222222222222222', 1000);
INSERT INTO Cuenta (nombre, apellidos, numCuenta, saldo)
VALUES ('Quique', 'Muñóz', 'ES333333333333333333333333', 3000);
 
INSERT INTO CuentaAhorro (nombre, apellidos, numCuenta, saldo, interes, fechaCreacion, accesoAux)
VALUES ('Indiana', 'Jones', 'ES444444444444444444444444', 30000, 4, '1981-06-12', '2000-02-24')
 
*/
