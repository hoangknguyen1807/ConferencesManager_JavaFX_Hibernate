module com.confmanage {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires java.persistence;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    requires jbcrypt;

    opens com.confmanage to javafx.fxml;
    opens com.confmanage.entities to org.hibernate.orm.core, javafx.base;
    opens com.confmanage.controllers to javafx.fxml;
    exports com.confmanage;
}