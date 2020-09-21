package backend

import grails.gorm.transactions.Transactional
import org.nitryx.Stock

import java.math.RoundingMode
import java.time.LocalDateTime

@Transactional
class StockService {

    def serviceMethod() {

    }

    def getStocksByCompany(String company) {
        return Stock.findAll("from Stock s "
                + "where s.company.name = :name",
                [name:company]
        ).toList();
    }

    def getStocksByCompanyAndAfterTime(String company, int hours) {
        def before = System.currentTimeMillis();
        def stocks = Stock.findAll("from Stock s "
            + "where s.company.name = :name and s.date >= :limit",
            [name:company, limit:LocalDateTime.now().minusHours(hours)]
        );
        stocks.forEach({ stock ->
            println stock.quote();
        })
        def after = System.currentTimeMillis();
        println "total execution time: " + (after - before) + " ms";
        println "Stocks size: " + stocks.size();
    }

    def calculateSD(List stocks) {
        def average = stocks.stream().mapToDouble({ stock -> stock.getPrice() }).average();
        if (!average.isPresent()) {
            return -1;
        }
        def sum = stocks.stream().mapToDouble({ stock -> Math.pow(stock.getPrice() - average.getAsDouble(), 2)}).sum();
        return BigDecimal.valueOf(Math.sqrt(sum/ stocks.size())).setScale(2, RoundingMode.HALF_UP).toDouble();
    }
}
