package backend

import grails.gorm.transactions.Transactional
import org.nitryx.Company

@Transactional
class CompanyService {

    def serviceMethod() {

    }

    def findAll() {
        return Company.findAll();
    }
}
