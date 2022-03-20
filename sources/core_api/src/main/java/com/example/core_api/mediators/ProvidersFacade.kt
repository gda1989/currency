package com.example.core_api.mediators

import com.example.database.AppProvider
import com.example.database.DatabaseProvider

interface ProvidersFacade : MediatorsProvider, DatabaseProvider, AppProvider