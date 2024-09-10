package com.example.quanlynhansu.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quanlynhansu.ui.ChangePasswordViewModel
import com.example.quanlynhansu.ui.LoginViewModel
import com.example.quanlynhansu.ui.RegisterViewModel
import com.example.quanlynhansu.ui.UpdateProfileViewModel

@Composable
fun AppNavigationGraph(
    loginViewModel: LoginViewModel = viewModel(),
    registerViewModel: RegisterViewModel = viewModel(),
    updateProfileViewModel: UpdateProfileViewModel = viewModel(),
    changePasswordViewModel: ChangePasswordViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN_SCREEN) {
        composable(Routes.LOGIN_SCREEN) {
            LoginScreen(
                loginViewModel,
                showHomeScreen = { userID, fullname, position, role ->
                    navController.navigate("${Routes.HOME_SCREEN}/$userID/$fullname/$position/$role")
                },
                showRegisterScreen = {
                    navController.navigate(Routes.REGISTER_SCREEN)
                }
            )
        }

        composable(Routes.REGISTER_SCREEN) {
            RegisterScreen(
                registerViewModel,
                backLoginScreen = {
                    navController.popBackStack(Routes.LOGIN_SCREEN, inclusive = false, saveState = true)
                }
            )
        }

        composable("${Routes.HOME_SCREEN}/{${Routes.USER_ID}}/{${Routes.FULL_NAME}}/{${Routes.POSITION}}/{${Routes.ROLE}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.FULL_NAME) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.POSITION) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.ROLE) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            val fullname = it?.arguments?.getString(Routes.FULL_NAME)
            val position = it?.arguments?.getString(Routes.POSITION)
            val role = it?.arguments?.getString(Routes.ROLE)
            requireNotNull(userID)
            requireNotNull(fullname)
            requireNotNull(position)
            requireNotNull(role)
            HomeScreen(
                userID = userID,
                fullname = fullname,
                position = position,
                role = role,
                backLoginScreen = {
                    navController.popBackStack(Routes.LOGIN_SCREEN, inclusive = false, saveState = true)
                },
                showCheckInOutScreen = { userID, role ->
                    navController.navigate("${Routes.CHECK_IN_OUT_SCREEN}/$userID/$role")
                },
                showSalaryScreen = { userID, role ->
                    navController.navigate("${Routes.SALARY_SCREEN}/$userID/$role")
                },
                showTaskScreen = { userID, role ->
                    navController.navigate("${Routes.TASK_SCREEN}/$userID/$role")
                },
                showInfoScreen = { userID, role ->
                    navController.navigate("${Routes.INFO_SCREEN}/$userID/$role")
                },
                showStatisticalEmployeeScreen = {
                    navController.navigate(Routes.STATISTICAL_EMPLOYEE_SCREEN)
                }
            )
        }

        composable("${Routes.CHECK_IN_OUT_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.ROLE) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            val role = it?.arguments?.getString(Routes.ROLE)
            requireNotNull(userID)
            requireNotNull(role)
            CheckInOutScreen(
                userID = userID,
                role = role,
                backHomeScreen = {
                    navController.popBackStack("${Routes.HOME_SCREEN}/{${Routes.USER_ID}}/{${Routes.FULL_NAME}}/{${Routes.POSITION}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                },
                showCheckInOutHistoryScreen = { userID ->
                    navController.navigate("${Routes.CHECK_IN_OUT_HISTORY_SCREEN}/$userID")
                },
                showCheckInOutListScreen = {
                    navController.navigate(Routes.CHECK_IN_OUT_LIST_SCREEN)
                }
            )
        }

        composable("${Routes.CHECK_IN_OUT_HISTORY_SCREEN}/{${Routes.USER_ID}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            requireNotNull(userID)
            CheckInOutHistoryScreen(
                userID = userID,
                backCheckInOutScreen = {
                    navController.popBackStack("${Routes.CHECK_IN_OUT_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                }
            )
        }

        // admin
        composable(Routes.CHECK_IN_OUT_LIST_SCREEN) {
            CheckInOutListScreen(
                backCheckInOutScreen = {
                    navController.popBackStack("${Routes.CHECK_IN_OUT_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                }
            )
        }

        composable("${Routes.SALARY_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.ROLE) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            val role = it?.arguments?.getString(Routes.ROLE)
            requireNotNull(userID)
            requireNotNull(role)
            SalaryScreen(
                userID = userID,
                role = role,
                backHomeScreen = {
                    navController.popBackStack("${Routes.HOME_SCREEN}/{${Routes.USER_ID}}/{${Routes.FULL_NAME}}/{${Routes.POSITION}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                },
                showMySalaryScreen = { userID ->
                    navController.navigate("${Routes.MY_SALARY_SCREEN}/$userID")
                },
                showSalaryListScreen = {
                    navController.navigate(Routes.SALARY_LIST_SCREEN)
                }
            )
        }

        composable("${Routes.MY_SALARY_SCREEN}/{${Routes.USER_ID}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            requireNotNull(userID)
            MySalaryScreen(
                userID = userID,
                backSalaryScreen = {
                    navController.popBackStack("${Routes.SALARY_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                }
            )
        }

        // admin
        composable(Routes.SALARY_LIST_SCREEN) {
            SalaryListScreen(
                backSalaryScreen = {
                    navController.popBackStack("${Routes.SALARY_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                }
            )
        }

        composable("${Routes.TASK_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.ROLE) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            val role = it?.arguments?.getString(Routes.ROLE)
            requireNotNull(userID)
            requireNotNull(role)
            TaskScreen(
                userID = userID,
                role = role,
                backHomeScreen = {
                    navController.popBackStack("${Routes.HOME_SCREEN}/{${Routes.USER_ID}}/{${Routes.FULL_NAME}}/{${Routes.POSITION}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                },
                showTaskListScreen = { userID ->
                    navController.navigate("${Routes.TASK_LIST_SCREEN}/$userID")
                }
            )
        }

        composable("${Routes.TASK_LIST_SCREEN}/{${Routes.USER_ID}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            requireNotNull(userID)
            TaskListScreen(
                userID = userID,
                backTaskScreen = {
                    navController.popBackStack("${Routes.TASK_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                }
            )
        }

        composable("${Routes.INFO_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.ROLE) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            val role = it?.arguments?.getString(Routes.ROLE)
            requireNotNull(userID)
            requireNotNull(role)
            InfoScreen(
                userID = userID,
                role = role,
                backHomeScreen = {
                    navController.popBackStack("${Routes.HOME_SCREEN}/{${Routes.USER_ID}}/{${Routes.FULL_NAME}}/{${Routes.POSITION}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                },
                showProfileScreen = { userID ->
                    navController.navigate("${Routes.PROFILE_SCREEN}/$userID")
                },
                showUpdateProfileScreen = { userID, role ->
                    navController.navigate("${Routes.UPDATE_PROFILE_SCREEN}/$userID/$role")
                },
                showChangePasswordScreen = { userID ->
                    navController.navigate("${Routes.CHANGE_PASSWORD_SCREEN}/$userID")
                },
                // admin
                showEmployeeProfileScreen = {
                    navController.navigate(Routes.EMPLOYEE_PROFILE_SCREEN)
                },
                showUpdateEmployeeProfileScreen = { role ->
                    navController.navigate("${Routes.UPDATE_EMPLOYEE_PROFILE_SCREEN}/$role")
                },
                showStatisticalEmployeeScreen = {
                    navController.navigate(Routes.STATISTICAL_EMPLOYEE_SCREEN)
                },
                showChangeUserAccountScreen = {
                    navController.navigate(Routes.CHANGE_USER_ACCOUNT_SCREEN)
                }
            )
        }

        composable("${Routes.PROFILE_SCREEN}/{${Routes.USER_ID}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                })
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            requireNotNull(userID)
            ProfileScreen(
                userID = userID,
                backPreviousScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable("${Routes.UPDATE_PROFILE_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.ROLE) {
                    type = NavType.StringType
                }
            )
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            val role = it?.arguments?.getString(Routes.ROLE)
            requireNotNull(userID)
            requireNotNull(role)
            UpdateProfileScreen(
                userID = userID,
                role = role,
                updateProfileViewModel,
                backPreviousScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.STATISTICAL_EMPLOYEE_SCREEN) {
            StatisticalEmployeeScreen(
                backPreviousScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable("${Routes.CHANGE_PASSWORD_SCREEN}/{${Routes.USER_ID}}",
            arguments = listOf(
                navArgument(name = Routes.USER_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val userID = it?.arguments?.getString(Routes.USER_ID)
            requireNotNull(userID)
            ChangePasswordScreen(
                userID = userID,
                changePasswordViewModel,
                backInfoScreen = {
                    navController.popBackStack("${Routes.INFO_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                }
            )
        }

        // admin
        composable(Routes.EMPLOYEE_PROFILE_SCREEN) {
            EmployeeProfileScreen(
                backInfoScreen = {
                    navController.popBackStack("${Routes.INFO_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                },
                showProfileScreen = { userID ->
                    navController.navigate("${Routes.PROFILE_SCREEN}/$userID")
                }
            )
        }

        composable("${Routes.UPDATE_EMPLOYEE_PROFILE_SCREEN}/{${Routes.ROLE}}",
            arguments = listOf(
                navArgument(name = Routes.ROLE) {
                    type = NavType.StringType
                }
            )
        ) {
            val role = it?.arguments?.getString(Routes.ROLE)
            requireNotNull(role)
            UpdateEmployeeProfileScreen(
                role = role,
                backInfoScreen = {
                    navController.popBackStack("${Routes.INFO_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                },
                showProfileScreen = { userID ->
                    navController.navigate("${Routes.PROFILE_SCREEN}/$userID")
                },
                showUpdateProfileScreen = { userID, role ->
                    navController.navigate("${Routes.UPDATE_PROFILE_SCREEN}/$userID/$role")
                }
            )
        }

        composable(Routes.CHANGE_USER_ACCOUNT_SCREEN) {
            ChangeUserAccountScreen(
                backInfoScreen = {
                    navController.popBackStack("${Routes.INFO_SCREEN}/{${Routes.USER_ID}}/{${Routes.ROLE}}", inclusive = false, saveState = true)
                }
            )
        }
    }
}