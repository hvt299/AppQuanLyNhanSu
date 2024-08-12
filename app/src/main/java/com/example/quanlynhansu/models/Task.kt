package com.example.quanlynhansu.models

import com.google.firebase.firestore.Exclude

data class Task(
    @Exclude var taskID: String,
    var employeeID: String,
    var taskTitle: String,
    var taskContent: String,
    var taskStatus: String,
    var taskStart: String,
    var taskEnd: String
)