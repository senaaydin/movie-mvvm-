package com.sena.movieapp.internal.injection.component

import com.sena.movieapp.internal.injection.scope.FragmentScope
import com.sena.movieapp.internal.injection.viewmodel.ViewModelModule
import com.sena.movieapp.ui.MovieListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ViewModelModule::class])
interface FragmentComponent {

    fun inject(fragment: MovieListFragment)

}