package proyecto.banco;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class CuentaAhorro extends Cuenta {

    double interes;
    LocalDate fechaCreacion;
    LocalDate accesoAux;

    public CuentaAhorro(String nombre, String apellidos, double saldo,ArrayList<Cuenta> cuentas) {
        super(nombre, apellidos, saldo, cuentas);
        this.numCuenta = generarNumCuenta(cuentas);
        this.interes = 4;
        fechaCreacion = LocalDate.now();
        accesoAux= LocalDate.now();
    }
    
        public CuentaAhorro(String nombre, String apellidos, double saldo,String numCuenta, double interes,LocalDate fechaCreacion, LocalDate accesoAux) {
        super(nombre, apellidos, saldo, numCuenta);
        this.numCuenta = numCuenta;
        this.interes = interes;
        this.fechaCreacion = fechaCreacion;
        this.accesoAux= accesoAux;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    @Override
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getAccesoAux() {
        return accesoAux;
    }

    public void setAccesoAux(LocalDate accesoAux) {
        this.accesoAux = accesoAux;
    }
        
        
        

     @Override
    public void calcularInteres() {
        LocalDate ahora = LocalDate.now();
        long meses = ChronoUnit.MONTHS.between(accesoAux, ahora);
        if (meses > 0) {
            //Trunco el dinero para tener máximo dos decimales
            setSaldo(Math.floor((( getSaldo() + (interes / 12 * meses)))*100)/100);
            accesoAux = LocalDate.now();
        }
    }



        @Override
    public String toString() {
        return super.toString() + "\nInterés: " + interes +"%" + "\nFecha de creación: " + fechaCreacion + "\nÚltimo cobro de intereses: " + accesoAux;
    }
    
}
