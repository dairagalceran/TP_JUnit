package TestJunit;

import org.junit.jupiter.api.*;
import restaurant.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static restaurant.StockOld.consumirIngredientes;

public class ProductoTest {

    static List<Producto> productos;
    public static final float MARGEN = (float) 0.2;
    public int numeroAleatorio;
    static Receta hamburguesa;

    @BeforeAll
    public static void listarProductos() {
        productos = new ArrayList<Producto>();
        Producto p2 = new ProductoBasico("Lata Sprite", 190, 250);
        Producto p3 = new ProductoBasico("Cerveza Quilmes", 360, 450);
        Producto p4 = new ProductoBasico("Media luna", 150, 200);
        Producto p5 = new ProductoBasico("Agua mineral", 140, 165);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);
        productos.add(p5);
        System.out.println("BeforeAll cantidad  de productos: " + productos.size());
    }

    @BeforeEach
    public void elegirUnProducto() throws Exception {
        Random generadorAleatorios = new Random();
        //genera un numero entre 1 y ingATestear.size() y lo guarda en la variable numeroAleatorio
        numeroAleatorio = generadorAleatorios.nextInt(productos.size());
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        //System.out.println("AfterAll cantiad Usuarios: "+productos.size());
        System.out.println("ProductoTest  @afterAll");
    }


    @Test
    @RepeatedTest(value = 5)
    public void testGananciaMayor20PorcientoProductoSimple() {
        Producto pActual = productos.get(numeroAleatorio);
        System.out.println(pActual);
        Float margenGananciaProdutoActual = (pActual.getPrecioUnitarioVenta() - pActual.getPrecioUnitarioCompra())
                / pActual.getPrecioUnitarioCompra();
        Assertions.assertTrue(margenGananciaProdutoActual > MARGEN);
    }

    @Test
    public void testGananciaMayor20PorcientoProductoEleborado() {

        hamburguesa = new Receta("HamburQueso", 25, 1100);

        Ingrediente i1 = new Ingrediente("carne picada", "1", 30, 3);
        Ingrediente i2 = new Ingrediente("pan Hamburguesa", "1", 100, (float) 65.90);
        Ingrediente i3 = new Ingrediente("fetas queso", "1", 38, (float) 50.80);

        hamburguesa.addIngrediente(i1, 350);
        hamburguesa.addIngrediente(i3, 2);
        hamburguesa.addIngrediente(i2, 1);

        float precioCosto = hamburguesa.calcularCosto();
        float margenGananciaRecetaActual = ((hamburguesa.getPrecioVenta() - precioCosto) / precioCosto);
        System.out.println("precio hamburguesa " + precioCosto);
        System.out.println("margen ganancia " + margenGananciaRecetaActual);

        Assertions.assertTrue(margenGananciaRecetaActual > MARGEN);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestProductosGanancia(){
        return ProductoTest.productos.stream()
                .map(dom -> DynamicTest.dynamicTest("Testing ganacias: "+dom, () ->{
                    Producto pActual = dom;
                    Float margenGananciaProdutoActual = (pActual.getPrecioUnitarioVenta() - pActual.getPrecioUnitarioCompra())
                            / pActual.getPrecioUnitarioCompra();
                    Assertions.assertTrue(margenGananciaProdutoActual > MARGEN);

                }));
    }


}