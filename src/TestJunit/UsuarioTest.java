package TestJunit;

import org.junit.Before;
import org.junit.jupiter.api.*;
import restaurant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioTest {

    static List<Usuario> ingATestear;
    int   numeroAleatorio;

    public UsuarioTest(){}

    @BeforeAll
    public static void  InitClass() {
        ingATestear =  new ArrayList<Usuario>();
        ingATestear.add( new Usuario("Marta Arteche", 1, 1000,"marta@gmail.com"));
        ingATestear.add(new  Usuario("Homero Simpson", 0, 350, "homerojsimpsonpringfield.com"));
        ingATestear.add(  new Usuario("Pablo delPo", 2, 15,"pablo@com"));
        ingATestear.add(  new Usuario("patricio", 2, 170,"papa@teororr.org"));
        System.out.println("BeforeAll cantiad Usuarios: "+ingATestear.size());
    }

    @BeforeEach
    public void elegirUno() throws Exception {
        Random generadorAleatorios = new Random();
        //genera un numero entre 1 y ingATestear.size() y lo guarda en la variable numeroAleatorio
        numeroAleatorio = generadorAleatorios.nextInt(ingATestear.size());
        System.out.println("BeforeEach");
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception{
        System.out.println("AfterAll cantiad Usuarios: "+ingATestear.size());
        //System.out.println("UsuarioTest  @afterAll");
    }

    @Test
    public void testVerificarPedidoReduceSaldoUsuario() throws SinSaldoException {
        float saldo = ingATestear.get(numeroAleatorio).getSaldoCuenta();
        System.out.println(saldo);
        Usuario uActual = ingATestear.get(numeroAleatorio);
        Producto prod = new ProductoBasico("Lata Sprite", 190, 250);
        ItemPedido itemPedido = new ItemPedido(2,prod);
        List<ItemPedido> listadoItems = new ArrayList<>();
        listadoItems.add(itemPedido);
        Pedido p = new Pedido(listadoItems, uActual );
        float compra = p.totalPedido();
        uActual.descontarSaldo(compra);
        System.out.println("compra importe "+compra);
        float saldoActualCompraRealizada = uActual.getSaldoCuenta();
        System.out.println("saldo actualizado "+saldoActualCompraRealizada);
        Assertions.assertTrue(saldo > saldoActualCompraRealizada);
    }

    @Test
    @RepeatedTest(value =6)
    @DisplayName("No se puede crear un usuario con un correo electrónico inválido")
    public void testVeridficarAltaUsuarioCorreoFormatoCorrecto(){
        String email = ingATestear.get(numeroAleatorio).getEmail();
        System.out.println(ingATestear.get(numeroAleatorio));
        /*
            Patron de mail que contenga:
                1) cadena de caracteres aA-zZ, numeros 0-9 y caracteres especiales "_", "-" y "."
                2) luego de la cadena de caractreres que exista un caracter arroba"@"
                3) luego del caracter arroba, que exista al menos un punto "."
        */
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email); // verifica que el email coincida con el patron

        Assertions.assertTrue(matcher.matches(), "Correo electrónico no cumple con el formato esperado");
    }


    // get(0) usuario diferente  PASA , get(1) agrego mismo usuario FAIL
    @Test
    @RepeatedTest(value = 6)
    public  void testIntentarAgregarUsuarioRepetido(){
        Usuario u = new Usuario("Pablo delPo", 2, 15,"pablo@com");
        ingATestear.add(u);
        System.out.println(u);
        Usuario uGuardado = ingATestear.get(numeroAleatorio);
        System.out.println(uGuardado);
        Assertions.assertNotEquals(u, uGuardado, "Se agrega usuario repetido");

    }

    @TestFactory
    Stream<DynamicTest> dynamicTestIngresarUsuarioConFormatoCorrectoDeCorreo() {
        return UsuarioTest.ingATestear.stream()
                .map(dom -> DynamicTest.dynamicTest("Testing: "+dom , () -> {
                    String email = dom.getEmail();
                    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email); // verifica que el email coincida con el patron
                    Assertions.assertTrue(matcher.matches(), "Correo electrónico no cumple con el formato esperado");
                }));
    }

}
