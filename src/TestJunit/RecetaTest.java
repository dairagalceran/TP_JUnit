package TestJunit;

import org.junit.jupiter.api.*;
import restaurant.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static restaurant.StockOld.consumirIngredientes;

public class RecetaTest {

    static List<Consumible> listado = new ArrayList<>();
    static Consumible i1;
    static Consumible i2;
    static Consumible i3;
    static Receta hamburguesa;

    @BeforeAll
    public static void  InitClass() {
        i1 = new Ingrediente("carne picada", "1", 230, 3);
        i2 = new Ingrediente("pan Hamburguesa", "1", 100, (float) 65.90);
        i3 = new Ingrediente("fetas queso", "1", 38, (float) 50.80);

        listado.add(i1);
        listado.add(i2);
        listado.add(i3);
        System.out.println("BeforeAll => cantidad  de consumibles : " + listado.size());
    }


    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        //System.out.println("AfterAll cantiad Usuarios: "+productos.size());
        System.out.println("RecetaTest   @afterAll");
    }


    @Test
    @DisplayName("Verificar stock para elaborar receta")
    public void testExpectedSinSuficientesIngredientes() {
        hamburguesa = new Receta("HamburQueso", 25, 1100);
        int i = 0;

        ItemReceta it1 = new ItemReceta((Ingrediente) i1, 350);
        ItemReceta it2 = new ItemReceta((Ingrediente) i2, 2);
        ItemReceta it3 = new ItemReceta((Ingrediente) i3, 1);

        hamburguesa.addIngrediente(it1);
        hamburguesa.addIngrediente(it2);
        hamburguesa.addIngrediente(it3);
        try {
            Iterator<ItemReceta> it = hamburguesa.getIngredientes().iterator();
            while (it.hasNext()) {
                ItemReceta item = it.next();
                consumirIngredientes( listado.get(i) , item.getCantidad());
                System.out.println(listado.get(i).getNombre());
                System.out.println(listado.get(i).getStock());

                i++;
            }

        } catch (SinSuficientesIngredientesException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testAlUnIngredientePorReceta(){
        Receta r = new Receta("lasagna", 40, 2300);
        //Receta r = hamburguesa;
        Assertions.assertTrue(r.getIngredientes().size() >0);
    }

}
