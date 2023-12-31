module com.example.project_for_university {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.google.gson;
    requires json.lib;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.example.project_for_university to javafx.fxml;
    exports com.example.project_for_university;
    exports com.example.project_for_university.controllers;
    opens com.example.project_for_university.controllers to javafx.fxml;
    exports com.example.project_for_university.controllers.loader;
    opens com.example.project_for_university.controllers.loader to javafx.fxml;
    exports com.example.project_for_university.controllers.user;
    opens com.example.project_for_university.controllers.user to javafx.fxml;
    exports com.example.project_for_university.http to com.fasterxml.jackson.databind;
    exports com.example.project_for_university.utils;
    opens com.example.project_for_university.utils to javafx.fxml;
    exports com.example.project_for_university.controllers.filter;
    opens com.example.project_for_university.controllers.filter to javafx.fxml;
    opens com.example.project_for_university.dto to javafx.fxml;
    opens com.example.project_for_university.controllers.user.admin to javafx.fxml;
    opens com.example.project_for_university.controllers.user.admin.models to javafx.base;
    exports com.example.project_for_university.controllers.material;
    opens com.example.project_for_university.controllers.material to javafx.fxml;
    exports com.example.project_for_university.controllers.material.tables;
    opens com.example.project_for_university.controllers.material.tables to javafx.fxml;
    exports com.example.project_for_university.dto.forBackend.entity to com.google.gson, json.lib;
    opens com.example.project_for_university.dto.forBackend.entity to com.google.gson;
    opens com.example.project_for_university.dto.forBackend.entity.types to com.google.gson;
    opens com.example.project_for_university.controllers.material.models to javafx.base;
}