引用方式
<repositories>
        <repository>
            <id>maven-repo-master</id>
            <url>https://raw.github.com/xunmengyiran/CommonUtils/master/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>

            <repository>
                <snapshots>
                    <enabled>true</enabled>
                    <updatePolicy>always</updatePolicy>
                </snapshots>
                <id>jcenter-releases</id>
                <name>jcenter</name>
                <url>http://jcenter.bintray.com</url>
            </repository>
    </repositories>

        <dependency>
            <groupId>com.xunmengyiran</groupId>
            <artifactId>CommonUtils</artifactId>
            <version>1.0.1</version>
        </dependency>
