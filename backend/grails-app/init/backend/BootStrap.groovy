package backend;

import org.nitryx.*

import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId;

class BootStrap {

    def init = { servletContext ->

        println "Testing before start..."

        def amazon = new Company("AMAZON", "Sales");
        amazon.save();
        //
        def google = new Company("GOOGLE", "Technology");
        google.save();
        //
        def iguatemi = new Company("IGUATEMI", "Shopping Centers");
        iguatemi.save();
        //
        def today = LocalDate.now(ZoneId.of("America/Fortaleza"));
        def current;
        //
        def min = 2.34;
        def max = 18.27;
        def random = new Random();
        //
        Arrays.asList(amazon, google, iguatemi).forEach({ company ->
            current = today.minusDays(30);
            while (current.compareTo(today) <= 0) {
                //
                def workHour = LocalTime.of(10, 0);
                while (workHour.compareTo(LocalTime.of(18, 0)) <= 0) {
                    def price = min + (random.nextDouble() * (max - min));
                    new Stock(
                        BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).toDouble(),
                        current.atTime(workHour),
                        company
                    ).save();
                    workHour = workHour.plusMinutes(1);
                }
                current = current.plusDays(1);
            }
        });

        println "almost all done...";

        new StockService().getStocksByCompanyAndAfterTime("AMAZON", 10);
        println "all stocks size -> " + Stock.findAll().size();
    }

    def destroy = {
    }
}
