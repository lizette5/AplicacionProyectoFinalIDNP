//
// Created by USER on 11/25/2020.
//

#ifndef APLICACION_KEEPSTATENAVIGATOR_H
#define APLICACION_KEEPSTATENAVIGATOR_H

@Navigator.Name("keep_state_fragment") // `keep_state_fragment` is used in navigation xml
class KeepStateNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction : FragmentTransaction = manager.beginTransaction()

        val currentFragment: Fragment? = manager.primaryNavigationFragment

        var initialNavigate = false
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        } else {
            initialNavigate = true
        }

        var fragment: Fragment? = manager.findFragmentByTag(tag)

        if (fragment == null) {
            fragment =
                manager.fragmentFactory.instantiate(context.classLoader, destination.className)
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.attach(fragment)
        }

        transaction.apply {
            setPrimaryNavigationFragment(fragment)
            setReorderingAllowed(true)
        }.commitNow()

        return if (initialNavigate) {
            destination
        } else {
            null
        }
    }
}



#endif //APLICACION_KEEPSTATENAVIGATOR_H
