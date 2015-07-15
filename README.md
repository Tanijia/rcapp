#Real-time Compile Java Code Web App

---
Provide online compile java code, and show the results.


### Environment Required
- JDK7+
- Tomcat7+
- Chrome

### Usage
Import in your ide then configure build path.

### Defect
About 10 second delay for run same name class.  
Because it's should wait for org.apache.catalina.core.StandardContext to reload.
