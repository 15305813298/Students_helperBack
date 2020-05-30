package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.model.Address
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.repository.AddressRepository
import cn.lintyone.out.helper.service.AddressService
import com.github.wenhao.jpa.Specifications
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl : AddressService {

    @Autowired
    lateinit var addressRepository: AddressRepository

    override fun get(id: Int): Address {
        val optional = addressRepository.findById(id)
        return if (optional.isPresent) {
            optional.get()
        } else {
            throw MyException("地址不存在")
        }
    }

    override fun create(user: User, description: String): Address {
        val address = Address()
        address.description = description
        address.user = user
        addressRepository.save(address)
        return address
    }

    override fun list(user: User): List<Address> {
        return addressRepository.findByUser(user)
    }

}