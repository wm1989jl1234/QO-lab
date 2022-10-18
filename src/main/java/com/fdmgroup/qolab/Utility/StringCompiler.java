package com.fdmgroup.qolab.Utility;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.fdmgroup.qolab.model.SourceFile;

/**
 * 
 * StringCompiler is responsible for running the Java compiler on a set of SourceFile objects and starting a new 
 * JVM with the compilation result. .java and .class files are saved in ./trainee_code/{dirName}.
 * currently only supports Windows.
 * 
 * @author Ian Jensen
 *
 */
public class StringCompiler{
	
	/**
	 * the directory name to place all trainee's code
	 */
    private final static String rootPath = "trainee_code"; 

    /**
     * the String containing all compilation errors if there are any.
     * will be populated after a call to compileAndRun
     */
    private String compilationErrors;

	public StringCompiler(){
		super();
	}
	
	/**
	 * 
	 * @param dirName the name to give the subdirectory in which the .java and .class files are saved
	 * @param sources list of SourceFiles to compile
	 * @return returns a Process object that encapsulates the new JVM on which the compiled SourceFiles are run.
	 * returns null if compilation failed or if the ProcessBuilder failed to start the process.
	 */
	public Process compileAndRun(String dirName, List<SourceFile> sources) {
		
		// Save source in .java files. 
		File root = new File(rootPath);
		List<File> sourceFiles = new ArrayList<File>();
		for(SourceFile s : sources) {
			File newFile = new File(root, dirName+"/"+ s.getTitle() + ".java");
			sourceFiles.add(newFile);
			newFile.getParentFile().mkdirs();
		}

		OpenOption myOptions[] = {StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
		
		for(int i = 0; i < sourceFiles.size(); i++) {
			try {
				Files.write(sourceFiles.get(i).toPath(), sources.get(i).getBodyText().getBytes(StandardCharsets.UTF_8),myOptions);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Compile source files.
		String sourcePaths[] = new String[sourceFiles.size()];
		for(int i = 0; i < sourcePaths.length; i++) {
			sourcePaths[i] = sourceFiles.get(i).getPath();
		}
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ByteArrayOutputStream errout = new ByteArrayOutputStream();
		int compileResult = compiler.run(null, stdout, errout, sourcePaths);
		
		// save the compilation errors and return null if compilation failed
		compilationErrors = new String( stdout.toByteArray(), java.nio.charset.StandardCharsets.UTF_8 ) + new String( errout.toByteArray(), java.nio.charset.StandardCharsets.UTF_8 );
		if(compileResult != 0 )
			return null;
		
		// run compiled code with shell command
		ProcessBuilder processBuilder = new ProcessBuilder();
        // this code will probably only work on Windows
        processBuilder.command("java", "Main");
        processBuilder.directory( new File( rootPath + "/" + dirName )); 

        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return process;
	}
	

	/**
	 * 
	 * @return compilation errors following a call to compileAndRun if there are any. 
	 */
	public String getCompilationErrors() {
		return compilationErrors;
	}
}
