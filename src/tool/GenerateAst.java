package tool;

import cc.landfill.Expr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if(args.length !=1){
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right"
        ));

    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" +baseName + ".java";
        PrintWriter writer =new PrintWriter(path, "UTF-8");

        writer.println("package cc.landfill.lox");
        writer.println();
        writer.println("import java.util.list");
        writer.println();
        writer.println("abstract class" +baseName + "{");

        writer.println("}");
        writer.close();


        //AST classes
        for(String type :types){
            String className = type.split(":")[0].trim();
            String fields = types.split(":")[1].trim();
            defineType(writer, baseName, className,fields);
        }
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList){
        
    }

}