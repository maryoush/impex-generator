impex-generator
===============

If you would like to embedd this ant task in your script 
here is the sample snippet.

{code}
<typedef name="impexGen" classname="de.hybris.platform.impexgen.velocity.ant.GenerateLocalizedImpexesTask" onerror="report">
				<classpath>
					<fileset dir="${ext.XXXXX.path}/lib">
						<include name="impex-generator-distribution-1.0-SNAPSHOT.jar"/>
				</fileset>
	<!-- all runtime dependencies --->

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
{code}