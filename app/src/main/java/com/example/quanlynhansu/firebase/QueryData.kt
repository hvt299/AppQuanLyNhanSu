package com.example.quanlynhansu.firebase

import android.annotation.SuppressLint
import com.example.quanlynhansu.models.Employee
import com.example.quanlynhansu.models.Salary
import com.example.quanlynhansu.models.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

// User Query
@SuppressLint("SuspiciousIndentation")
suspend fun getUserByUserID(userID: String): User {
    var updatedUser = User("", "", "", "", "", Timestamp.now(), Timestamp.now(), Timestamp.now())

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    try {
        val querySnapshot = db.collection("User")
            .whereEqualTo("userID", userID)
            .get()
            .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.firstOrNull()
                val userData = document?.data
                val username = userData?.get("username") as String
                val password = userData["password"] as String
                val status = userData["status"] as String
                val role = userData["role"] as String
                val lastLogin = userData["lastLogin"] as Timestamp
                val createdAt = userData["createdAt"] as Timestamp
                val updatedAt = userData["updatedAt"] as Timestamp
                updatedUser = User(userID, username, password, status, role, lastLogin, createdAt, updatedAt)
            }
    } catch (_: Exception) {}
    return updatedUser
}

suspend fun getAllUser(): List<User> {
    val userList: MutableList<User> = mutableListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val querySnapshot = db.collection("User").get().await()
    for (document in querySnapshot.documents) {
        val user = document.toObject(User::class.java)
        user?.userID = document.id
        user?.let {
            userList.add(it)
        }
    }
    return userList
}

// Employee Query
suspend fun getEmployeeByUserID(userID: String): Employee {
    var updatedEmployee = Employee("", "", "", "", Timestamp.now(), "", "", "", "", "", "", "", "", "", "", Timestamp.now(), Timestamp.now())

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    try {
        val querySnapshot = db.collection("Employee")
            .whereEqualTo("userID", userID)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.documents.firstOrNull()
            val employeeID = document?.id as String
            val employeeData = document.data
            val fullname = employeeData?.get("fullname") as String
            val gender = employeeData["gender"] as String
            val dateOfBirth = employeeData["dateOfBirth"] as Timestamp
            val idCard = employeeData["idCard"] as String
            val placeOfBirth = employeeData["placeOfBirth"] as String
            val placeOfResidence = employeeData["placeOfResidence"] as String
            val cityOrProvince = employeeData["cityOrProvince"] as String
            val district = employeeData["district"] as String
            val wardOrCommune = employeeData["wardOrCommune"] as String
            val phoneNumber = employeeData["phoneNumber"] as String
            val emailAddress = employeeData["emailAddress"] as String
            val department = employeeData["department"] as String
            val position = employeeData["position"] as String
            val startDate = employeeData["startDate"] as Timestamp
            val endDate = employeeData["endDate"] as Timestamp
            updatedEmployee = Employee(
                employeeID, userID, fullname, gender, dateOfBirth,
                idCard, placeOfBirth, placeOfResidence, cityOrProvince,
                district, wardOrCommune, phoneNumber, emailAddress,
                department, position, startDate, endDate)
        }
    } catch (_: Exception) {}
    return updatedEmployee
}

suspend fun getEmployeeByEmployeeID(employeeID: String): Employee {
    var employee = Employee()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    try {
        val querySnapshot = db.collection("Employee")
            .whereEqualTo("employeeID", employeeID)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.documents.firstOrNull()
            val employeeID = document?.id as String
            val employeeData = document.data
            val userID = employeeData?.get("userID") as String
            val fullname = employeeData["fullname"] as String
            val gender = employeeData["gender"] as String
            val dateOfBirth = employeeData["dateOfBirth"] as Timestamp
            val idCard = employeeData["idCard"] as String
            val placeOfBirth = employeeData["placeOfBirth"] as String
            val placeOfResidence = employeeData["placeOfResidence"] as String
            val cityOrProvince = employeeData["cityOrProvince"] as String
            val district = employeeData["district"] as String
            val wardOrCommune = employeeData["wardOrCommune"] as String
            val phoneNumber = employeeData["phoneNumber"] as String
            val emailAddress = employeeData["emailAddress"] as String
            val department = employeeData["department"] as String
            val position = employeeData["position"] as String
            val startDate = employeeData["startDate"] as Timestamp
            val endDate = employeeData["endDate"] as Timestamp
            employee = Employee(
                employeeID, userID, fullname, gender, dateOfBirth,
                idCard, placeOfBirth, placeOfResidence, cityOrProvince,
                district, wardOrCommune, phoneNumber, emailAddress,
                department, position, startDate, endDate)
        }
    } catch (_: Exception) {}
    return employee
}

suspend fun getAllEmployee(): List<Employee> {
    val employeeList: MutableList<Employee> = mutableListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val querySnapshot = db.collection("Employee").get().await()
    for (document in querySnapshot.documents) {
        val employee = document.toObject(Employee::class.java)
        employee?.employeeID = document.id
        employee?.let {
            employeeList.add(it)
        }
    }
    return employeeList
}

suspend fun getEmployeeQuantity(): Int {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    return try {
        val querySnapshot = db.collection("Employee").get().await()
        querySnapshot.size()
    } catch (e: Exception) {
        0
    }
}

suspend fun getEmployeeBaseOnAge(): List<Int> {
    val df = SimpleDateFormat("yyyy", Locale.getDefault())
    val employeeBaseOnAge = MutableList(4) { 0 }
    val db = FirebaseFirestore.getInstance()
    return try {
        val querySnapshot = db.collection("Employee").get().await()

        for (document in querySnapshot.documents) {
            val employee = document.toObject(Employee::class.java)
            employee?.let {
                val age = LocalDate.now().year - df.format(it.dateOfBirth.toDate()).toInt()
                when {
                    age > 45 -> employeeBaseOnAge[3]++
                    age in 36..45 -> employeeBaseOnAge[2]++
                    age in 26..35 -> employeeBaseOnAge[1]++
                    age in 18..25 -> employeeBaseOnAge[0]++
                    else -> {}
                }
            }
        }
        employeeBaseOnAge
    } catch (e: Exception) {employeeBaseOnAge}
}

suspend fun getEmployeeBaseOnStartDate(): List<Int> {
    val df = SimpleDateFormat("yyyy", Locale.getDefault())
    val employeeBaseOnStartDate = MutableList(5) { 0 }
    val db = FirebaseFirestore.getInstance()
    return try {
        val querySnapshot = db.collection("Employee").get().await()

        for (document in querySnapshot.documents) {
            val employee = document.toObject(Employee::class.java)
            employee?.let {
                val year = df.format(it.startDate.toDate()).toInt()
                if (year <= LocalDate.now().year) {
                    employeeBaseOnStartDate[4]++
                }
                if (year <= LocalDate.now().year - 1) {
                    employeeBaseOnStartDate[3]++
                }
                if (year <= LocalDate.now().year - 2) {
                    employeeBaseOnStartDate[2]++
                }
                if (year <= LocalDate.now().year - 3) {
                    employeeBaseOnStartDate[1]++
                }
                if (year <= LocalDate.now().year - 4) {
                    employeeBaseOnStartDate[0]++
                }
            }
        }
        employeeBaseOnStartDate
    } catch (e: Exception) {employeeBaseOnStartDate}
}

// Salary Query
suspend fun getSalaryByEmployeeID(employeeID: String): List<Salary> {
    val salaryList: MutableList<Salary> = mutableListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val querySnapshot = db.collection("Salary")
        .whereEqualTo("employeeID", employeeID)
        .orderBy("fromDate", Query.Direction.DESCENDING)
        .get()
        .await()

    for (document in querySnapshot.documents) {
        val salary = document.toObject(Salary::class.java)
        salary?.let {
            salaryList.add(it)
        }
    }
    return salaryList
}

suspend fun getAllSalary(): List<Salary> {
    val salaryList: MutableList<Salary> = mutableListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val querySnapshot = db.collection("Salary")
        .orderBy("fromDate", Query.Direction.DESCENDING)
        .get()
        .await()

    for (document in querySnapshot.documents) {
        val salary = document.toObject(Salary::class.java)
        salary?.let {
            salaryList.add(it)
        }
    }
    return salaryList
}

// CheckTime Query


// Task Query
