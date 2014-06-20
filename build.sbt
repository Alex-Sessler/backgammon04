name := "backgammon04"

version := "1.0-SNAPSHOT"

resolvers += "HTWG Resolver" at "http://lenny2.in.htwg-konstanz.de:8081/artifactory/libs-snapshot-local"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.springframework" % "spring-webmvc" % "4.0.3.RELEASE",
  "org.springframework" % "spring-context" % "4.0.3.RELEASE",
  "org.springframework" % "spring-orm" % "4.0.3.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.0.3.RELEASE",
  "org.springframework" % "spring-tx" % "4.0.3.RELEASE",
  "org.springframework" % "spring-test" % "4.0.3.RELEASE",
  "org.springframework.data" % "spring-data-mongodb" % "1.4.2.RELEASE",  
  "org.hibernate" % "hibernate-entitymanager" % "4.1.9.Final",
	"javax.el" % "el-api" % "2.2",
  "c3p0" % "c3p0" % "0.9.1.2",
  "backgammon04" % "backgammon04-persistence-interface" % "0.0.2-SNAPSHOT",
  "backgammon04" % "backgammon04-persistence-mysql" % "0.0.1-SNAPSHOT"
)  

play.Project.playJavaSettings
