package com.report_service.service;

import com.report_service.entities.ProductEntity;

import com.report_service.entities.Sale;
import com.report_service.entities.data.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportService {


    public List<SalesMonth> getSalesForMonth() {

        List<SalesMonth> salesMonths = new ArrayList<>();
        String months[] = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

        for (int i = 0; i < 12; i++) {
            SalesMonth salesMonth = new SalesMonth();
            salesMonth.setName(months[i]);
            salesMonth.setSales((int) (Math.random() * 100));
            salesMonth.setProducts((int) (Math.random() * 50));
            salesMonth.setCosts((int) (Math.random() * 1000));
            salesMonths.add(salesMonth);
        }

        return salesMonths;
    }


    public List<BestSellingProduct> getBestSellingProducts() {

        List<BestSellingProduct> bestSellingProducts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BestSellingProduct product = new BestSellingProduct();
            product.setName("Product " + (i + 1));
            product.setValue((Math.random() * 100));
            product.setRevenue(Math.random() * 1000);
            bestSellingProducts.add(product);
        }

        return bestSellingProducts;
    }

    public List<FinancialAnalysis> getFinancialAnalysis() {

        List<FinancialAnalysis> financialAnalyses = new ArrayList<>();
        FinancialAnalysis analysisRevenue = new FinancialAnalysis("Ingresos", 23001.32);
        FinancialAnalysis analysisCosts = new FinancialAnalysis("Costos", 15000.00);
        FinancialAnalysis analysisProfit = new FinancialAnalysis("Ganancias", 8001.32);

        financialAnalyses.add(analysisRevenue);
        financialAnalyses.add(analysisCosts);
        financialAnalyses.add(analysisProfit);

        return financialAnalyses;
    }

    public List<Statistics> getStatistics() {

        List<Statistics> statistics = new ArrayList<>();
        Statistics statistic = new Statistics("Ventas Totales", "S/ 18,550", "+12.5%", "TrendingUp", true);
        Statistics statistic2 = new Statistics("Margen de Beneficio", "26.3%", "+1.8%", "PieChart", true);
        Statistics statistic3 = new Statistics("Ticket Promedio", "S/ 149.60", "+5.2%", "Receipt", true);
        Statistics statistic4 = new Statistics("Costo de Adquisición", "S/ 38.50", "-2.1%", "TrendingDown", false);
        statistics.add(statistic);
        statistics.add(statistic2);
        statistics.add(statistic3);
        statistics.add(statistic4);

        return statistics;
    }

    public List<User> getUsers() {

        List<User> users = new ArrayList<>();
        User user1 = new User("CLI-2025-001", "María Quispe", "maria.quispe@correo.pe", "+51 987 654 321", "15/01/2025", 7, 529.85, "activo");
        User user2 = new User("CLI-2025-002", "Juan Huamán", "juan.huaman@correo.pe", "+51 912 345 678", "02/02/2025", 3, 189.5, "activo");
        User user3 = new User("CLI-2025-003", "Ana Mamani", "ana.mamani@correo.pe", "+51 934 567 890", "20/02/2025", 5, 345.75, "activo");
        User user4 = new User("CLI-2025-004", "Carlos Ticona", "carlos.ticona@correo.pe", "+51 945 678 901", "05/03/2025", 1, 67.25, "inactivo");
        User user5 = new User("CLI-2025-005", "Laura Paredes", "laura.paredes@correo.pe", "+51 956 789 012", "18/03/2025", 4, 289.99, "activo");
        User user6 = new User("CLI-2025-006", "Miguel Salazar", "miguel.salazar@correo.pe", "+51 967 890 123", "01/04/2025", 0, 0.0, "inactivo");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        return users;
    }


    public List<Product> getProducts() {

        List<Product> products = new ArrayList<>();

        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("Electrónica");
        tags1.add("Smartphone");
        ArrayList<String> highlights1 = new ArrayList<>();
        highlights1.add("Cámara de 12 MP");
        highlights1.add("Procesador de 2.5 GHz");
        ArrayList<Review> reviews1 = new ArrayList<>();
        reviews1.add(new Review("Juan Pérez", 5, "2025-06-01", "Excelente teléfono, la cámara es impresionante", 15));
        reviews1.add(new Review("María García", 5, "2025-06-01", "Muy buen teléfono, pero la batería podría ser mejor", 4));
        ArrayList<Integer> relatedProducts1 = new ArrayList<>();
        relatedProducts1.add(2);
        relatedProducts1.add(3);
        Product producto1 = new Product(
                1,
                "https://example.com/telefono.jpg",
                "Teléfono inteligente",
                "Teléfono con cámara de 12 MP y procesador de 2.5 GHz",
                "Teléfono con cámara de 12 MP y procesador de 2.5 GHz, pantalla de 6.1 pulgadas y batería de 4000 mAh",
                "Electrónica",
                999.99,
                "10%",
                4.5,
                200,
                100,
                true,
                "Plástico y metal",
                "15 cm x 7 cm x 0.5 cm",
                "150 g",
                "64 GB",
                "No lavar",
                "1 año",
                "China",
                tags1,
                highlights1,
                reviews1,
                relatedProducts1
        );

        // Instancia 2
        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add("Deporte");
        tags2.add("Ciclismo");
        ArrayList<String> highlights2 = new ArrayList<>();
        highlights2.add("Cuadro de aluminio");
        highlights2.add("Frenos de disco");
        ArrayList<Review> reviews2 = new ArrayList<>();
        reviews2.add(new Review("Pedro Martínez", 5, "2025-06-01", "Excelente bicicleta, muy ligera y maniobrable", 5));
        reviews2.add(new Review("Ana Rodríguez", 5, "2025-06-01", "Muy buena bicicleta, pero la sillita podría ser más cómoda", 4));
        ArrayList<Integer> relatedProducts2 = new ArrayList<>();
        relatedProducts2.add(1);
        relatedProducts2.add(4);
        Product producto2 = new Product(
                2,
                "https://example.com/bicicleta.jpg",
                "Bicicleta de montaña",
                "Bicicleta con cuadro de aluminio y frenos de disco",
                "Bicicleta con cuadro de aluminio y frenos de disco, ruedas de 26 pulgadas y transmisión de 21 velocidades",
                "Deporte",
                799.99,
                "15%",
                4.2,
                150,
                50,
                false,
                "Aluminio",
                "18 cm x 10 cm x 5 cm",
                "20 kg",
                "21 velocidades",
                "No lavar",
                "2 años",
                "Estados Unidos",
                tags2,
                highlights2,
                reviews2,
                relatedProducts2
        );

        // Instancia 3
        ArrayList<String> tags3 = new ArrayList<>();
        tags3.add("Electrónica");
        tags3.add("Tableta");
        ArrayList<String> highlights3 = new ArrayList<>();
        highlights3.add("Pantalla de 10.1 pulgadas");
        highlights3.add("Procesador de 1.8 GHz");
        ArrayList<Review> reviews3 = new ArrayList<>();
        reviews3.add(new Review("Luis García", 5, "2025-06-01", "Excelente tableta, la pantalla es muy buena", 5));
        reviews3.add(new Review("Sofía Rodríguez", 5, "2025-06-01", "Muy buena tableta, pero la batería podría ser mejor", 4));
        ArrayList<Integer> relatedProducts3 = new ArrayList<>();
        relatedProducts3.add(1);
        relatedProducts3.add(5);
        Product producto3 = new Product(
                3,
                "https://example.com/tableta.jpg",
                "Tableta Android",
                "Tableta con pantalla de 10.1 pulgadas y procesador de 1.8 GHz",
                "Tableta con pantalla de 10.1 pulgadas y procesador de 1.8 GHz, batería de 5000 mAh y cámara de 5 MP",
                "Electrónica",
                499.99,
                "20%",
                4.1,
                100,
                20,
                true,
                "Plástico",
                "25 cm x 15 cm x 1 cm",
                "400 g",
                "32 GB",
                "No lavar",
                "1 año",
                "China",
                tags3,
                highlights3,
                reviews3,
                relatedProducts3
        );



        products.add(producto1);
        products.add(producto2);
        products.add(producto3);


        return products;
    }


    //test
    public List<Sale> getSales() {
        List<Sale> sales = new ArrayList<>();
        Sale sale1 = new Sale(LocalDate.now(), new ProductEntity("Product1", 50), 2, 100.0);
        Sale sale2 = new Sale(LocalDate.now(), new ProductEntity("Product2", 50), 1, 50.0);
        sales.add(sale1);
        sales.add(sale2);
        return sales;
    }


}
