package proyecto.banco;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cuenta {

    private String nombre;
    private String apellidos;
    protected String numCuenta;
    private double saldo;

    public Cuenta(String nombre, String apellidos, double saldo, ArrayList<Cuenta> cuentas) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numCuenta = generarNumCuenta(cuentas);
        this.saldo = saldo;
    }

    public Cuenta(String nombre, String apellidos, double saldo, String numCuenta) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numCuenta = numCuenta;
        this.saldo = saldo;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDate getFechaCreacion() {
        return null;
    }

    public LocalDate getAccesoAux() {
        return null;
    }

    //Métodos Propios
    public String generarNumCuenta(ArrayList<Cuenta> cuentas) {

        String numCuenta;
        boolean repetido;

        do {
            int num = (int) (Math.random() * (99999999 - 11111111) + 11111111);
            int valor1;
            int valor2;

            do {
                valor1 = (int) (Math.random() * (90 - 65) + 65);
                valor2 = (int) (Math.random() * (90 - 65) + 65);
            } while (valor1 == valor2);

            char letra1 = (char) valor1;
            char letra2 = (char) valor2;
            numCuenta = letra1 + "" + letra2 + "" + num + "" + num + "" + num + "";

            //Compruebo que el número de cuenta no se repite con el resto
            repetido = false;
            int i = 0;
            while (i < cuentas.size() && !repetido) {
                Cuenta a = cuentas.get(i);
                if (a.getNumCuenta().equals(numCuenta)) {
                    repetido = true;
                }
                i++;
            }

        } while (repetido);

        return numCuenta;
    }

    public void ingresar(double cantidad) {
        saldo = saldo + cantidad;
    }

    public void retirar(double cantidad) {
        if (cantidad <= saldo) {
            saldo = saldo - cantidad;
        } else {
            System.out.println("La cantidad a retirar no puede ser superior al saldo de la cuenta.");
        }

    }

    public void calcularInteres() {
    }

    public void transferencia(int cuentaOrigen, int cuentaDestino, double dinero, ArrayList<Cuenta> contenedor) {

        //cuentaOrigen= i.  cuentaDestino = j. dinero = retiro .ArrayList = cuentas
        contenedor.get(cuentaOrigen).retirar(dinero);
        contenedor.get(cuentaDestino).ingresar(dinero);

    }

    @Override
    public String toString() {
        return "Cliente: " + nombre + " " + apellidos + "\nNúmero de cuenta: " + numCuenta + "\nSaldo:  \u001B[32m" + saldo + " €\u001B[37m";
    }

}
