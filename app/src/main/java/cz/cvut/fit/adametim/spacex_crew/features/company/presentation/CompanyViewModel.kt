package cz.cvut.fit.adametim.spacex_crew.features.company.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.adametim.spacex_crew.features.company.data.CompanyRepository
import cz.cvut.fit.adametim.spacex_crew.features.company.domain.Company
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CompanyViewModel(
    private val companyRepository: CompanyRepository
) : ViewModel() {

    private val _companyStateStream = MutableStateFlow<CompanyState>(CompanyState.Loading)
    val companyStateStream = _companyStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            loadCompany()
        }
        viewModelScope.launch {
            fetchCompany()
        }
    }

    private suspend fun loadCompany() {
        companyRepository.getCompanyStream().collect { company ->
            _companyStateStream.value = CompanyState.Loaded(company = company)
        }
    }

    private suspend fun fetchCompany() {
        try {
            companyRepository.fetchCompany()
        } catch (t: Throwable) {
            _companyStateStream.value = CompanyState.Error(throwable = t)
        }
    }
}

sealed class CompanyState {

    object Loading : CompanyState()

    data class Loaded(val company: Company) : CompanyState()

    data class Error(val throwable: Throwable) : CompanyState()
}
