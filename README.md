impex-generator
===============

# Build
Just run

> mvn clean install

Generated jar inside *impex-generator-distribution/target* should contain all code base - rest dependencies is right from 'platform'

# Embedding

If you would like to embedded this ant task in your script, here is the sample snippet.

```
<typedef name="impexGen" classname="xxx.platform.impexgen.velocity.ant.GenerateLocalizedImpexesTask" onerror="report">
				<classpath>
					<fileset dir="${ext.extension.path}/lib">
						<include name="impex-generator-distribution-1.0-SNAPSHOT.jar"/>
				</fileset>
					<fileset dir="${ext.core.path}/lib">
						<include name="*.jar"/>
					</fileset>
					<fileset dir="${platformhome}/bootstrap/bin/">
						<include name="*.jar"/>
					</fileset>
					<fileset dir="${platformhome}/lib">
						<include name="*.jar"/>
					</fileset>
					<fileset dir="${ant.home}/lib">
						<include name="ant*.jar"/>
					</fileset>
				</classpath>
			</typedef>
```