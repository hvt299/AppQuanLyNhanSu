package com.example.quanlynhansu.firebase

import android.annotation.SuppressLint
import com.example.quanlynhansu.models.Employee
import com.example.quanlynhansu.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@SuppressLint("SuspiciousIndentation")
suspend fun getUserByUserID(userID: String): User {
    var updatedUser = User("", "", "", "", "", "", "", "")

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
                val lastLogin = userData["lastLogin"] as String
                val createdAt = userData["createdAt"] as String
                val updatedAt = userData["updatedAt"] as String
                updatedUser = User(userID, username, password, status, role, lastLogin, createdAt, updatedAt)
            }
    } catch (_: Exception) {}
    return updatedUser
}

suspend fun getEmployeeByUserID(userID: String): Employee {
    var updatedEmployee = Employee("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")

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
            val dateOfBirth = employeeData["dateOfBirth"] as String
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
            val startDate = employeeData["startDate"] as String
            val endDate = employeeData["endDate"] as String
            updatedEmployee = Employee(
                employeeID, userID, fullname, gender, dateOfBirth,
                idCard, placeOfBirth, placeOfResidence, cityOrProvince,
                district, wardOrCommune, phoneNumber, emailAddress,
                department, position, startDate, endDate)
        }
    } catch (_: Exception) {}
    return updatedEmployee
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