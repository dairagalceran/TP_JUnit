package TestJunit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import restaurant.*;



    @RunWith(Suite.class)



    @Suite.SuiteClasses({
            Almacen.class,
            UsuarioTest.class,
            ProductoTest.class,
            RecetaTest.class

    })


    public class TestSuite {
        // CLASE SIEMPRE VACIA
    }

