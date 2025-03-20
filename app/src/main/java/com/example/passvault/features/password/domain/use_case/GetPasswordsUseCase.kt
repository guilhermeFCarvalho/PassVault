package com.example.passvault.features.password.domain.use_case

import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.repository.PasswordRepository
import com.example.passvault.features.password.domain.util.order_type.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPasswordsUseCase(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(passwordOrder: OrderType = OrderType.Descending): Flow<List<Password>> {
        return passwordRepository.getPasswords().map { passwords ->
            when (passwordOrder) {
                is OrderType.Ascending ->
                    passwords.sortedBy { it.label.lowercase() }

                is OrderType.Descending ->
                    passwords.sortedByDescending { it.label.lowercase() }


            }
        }
    }
}