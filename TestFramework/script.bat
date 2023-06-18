cd "C:\Users\frédéric\Desktop\Web Dyn\Framework\out\production\Framework"
jar cvf fw.jar *
copy "./fw.jar" "C:\Users\frédéric\Desktop\Web Dyn\TestFramework\WEB-INF\lib\"
cd "C:\Users\frédéric\Desktop\Web Dyn\TestFramework\WEB-INF\classes"
javac -cp "C:\Users\frédéric\Desktop\Web Dyn\TestFramework\WEB-INF\lib\fw.jar" -d . *.java
cd "C:\Users\frédéric\Desktop\Web Dyn\TestFramework"
jar cvf "TestFramework.war" *
copy "TestFramework.war" "C:\Program Files\Apache Software Foundation\Tomcat 10.1_Tomcat\webapps"
del "TestFramework.war"