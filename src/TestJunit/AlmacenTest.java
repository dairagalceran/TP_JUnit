import org.junit.jupiter.api.*;
import restaurant.Almacen;
import restaurant.Ingrediente;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class AlmacenTest {

    Ingrediente defaultIngrediente;

    //	static Ingrediente ingATestear[];
    static List<Ingrediente> ingATestear;

    @BeforeAll
    static void  InitClass() {
        ingATestear =  new  ArrayList<Ingrediente>();
        ingATestear.add( new Ingrediente("harina", "kg", 10,23));
        ingATestear.add(  new Ingrediente("aceilte", "ltd", 15,24));
        ingATestear.add(  new Ingrediente("papa", "kg", 17,25));
        ingATestear.add(  new Ingrediente("huevo", "un", 15,26));
        ingATestear.add( new Ingrediente("fideos", "kg", 10,27));

    }

    @BeforeEach
    void setUp() throws Exception {
        int cualTomamos = (int) (Math.random()*ingATestear.size());
        defaultIngrediente = AlmacenTest.ingATestear.get(cualTomamos);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @RepeatedTest(15)
    void testIngresarConsumibles() {
        // System.out.println(defaultIngrediente.toString());
        int cantidadInicial = defaultIngrediente.getStock();
        int cantidadAgregada = (int) (Math.random() *100);
        /** Me aseguro que el stock se esta actualizando bien*/
        Almacen.IngresarConsumibles(defaultIngrediente,  cantidadAgregada);
        // System.out.println("cantInicial "+cantidadInicial+" cantAgregada "+cantidadAgregada);
        //System.out.println(defaultIngrediente.getStock());
        assertEquals(cantidadInicial+cantidadAgregada, defaultIngrediente.getStock());
    }


    @Test
    @RepeatedTest(value = 10)
    void testExtraerConsumibles() {
        int cantidadInicial = defaultIngrediente.getStock();
        int cantidadUsada = (int)(Math.random()*20);
        Almacen.ExtraerConsumibles(defaultIngrediente, cantidadUsada);
        //System.out.println("cantInicial "+cantidadInicial+" cantAgregada "+cantidadUsada);
        //System.out.println(defaultIngrediente.getStock());
        Assertions.assertEquals(cantidadInicial-cantidadUsada , defaultIngrediente.getStock());
        Assertions.assertTrue(cantidadInicial-cantidadUsada >= 0);
    }


    @TestFactory
    Stream<DynamicTest> dynamicTestIngredientes() {
        return AlmacenTest.ingATestear.stream()
                .map(dom -> DynamicTest.dynamicTest("Testing: "+dom , () -> {
                    int cantidadInicial = dom.getStock();
                    int cantidadAgregada = (int) (Math.random() *100);
                    Almacen.IngresarConsumibles(dom, cantidadAgregada);
                    assertEquals(cantidadAgregada+cantidadInicial, dom.getStock());
                }));
    }

}
