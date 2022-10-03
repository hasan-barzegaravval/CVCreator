
module com.mycompany.mavenproject3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.junit.jupiter.api;  
    requires org.junit.jupiter.params; 
    opens com.mycompany.mavenproject3 to javafx.fxml,com.fasterxml.jackson.core;
    opens com.mycompany.mavenproject3.UIElements to javafx.base,com.fasterxml.jackson.core;
    exports com.mycompany.mavenproject3;
    exports com.mycompany.mavenproject3.Definitions;
    exports com.mycompany.mavenproject3.Commons;
    exports com.mycompany.mavenproject3.UIElements;    
}
