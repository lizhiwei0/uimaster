package org.shaolin.bmdp.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Files Operator
 */
public final class FileUtil {

    private FileUtil() {
    }

    public static void zip(File file, File zipFile) throws IOException {
        zipFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(zipFile);
        ZipOutputStream zout = null;
        try {
            zout = new ZipOutputStream(new BufferedOutputStream(fout));
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    fileToZip(files[i], zout, file);
                }
            } else if (file.isFile()) {
                fileToZip(file, zout, file.getParentFile());
            }
        } finally {
            if (zout != null) {
                zout.close();
            }
        }
    }

    private static void fileToZip(File file, ZipOutputStream zout, File baseDir) throws IOException {
        String entryName = file.getPath().substring(baseDir.getPath().length() + 1);
        if (File.separatorChar == '/') {
            entryName = entryName.replace(File.separator, "/");
        }
        if (file.isDirectory()) {
            zout.putNextEntry(new ZipEntry(entryName + "/"));
            try {
                zout.closeEntry();
            } catch (final IOException e) {
                // ignore
            }
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                fileToZip(files[i], zout, baseDir);
            }
        } else {
            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
                zout.putNextEntry(new ZipEntry(entryName));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    zout.write(buffer, 0, len);
                }
            } finally {
                try {
                    zout.closeEntry();
                } catch (final IOException e) {
                    // ignore
                }
                CloseUtil.close(is);
            }
        }
    }

    public static void unzip(String zip, String filePath) throws IOException {
        ZipFile zipFile = new ZipFile(zip);
        try {
            BufferedInputStream bufferInput = null;
            BufferedOutputStream bufferOutput = null;
            FileOutputStream output = null;
            Enumeration<ZipEntry> emu = (Enumeration<ZipEntry>) zipFile.entries();
            while (emu.hasMoreElements()) {
                ZipEntry entry = emu.nextElement();
                if (entry.isDirectory()) {
                    new File(filePath + entry.getName()).mkdirs();
                    continue;
                }
                try {
                    bufferInput = new BufferedInputStream(zipFile.getInputStream(entry));
                    File file = new File(filePath + "/" + entry.getName());
                    File parent = file.getParentFile();
                    if (parent != null && (!parent.exists())) {
                        parent.mkdirs();
                    }
                    output = new FileOutputStream(file);
                    bufferOutput = new BufferedOutputStream(output, 1024);
                    int count;
                    byte[] data = new byte[1024];
                    while ((count = bufferInput.read(data, 0, 1024)) != -1) {
                        bufferOutput.write(data, 0, count);
                    }
                    bufferOutput.flush();
                } finally {
                    CloseUtil.close(bufferInput);
                    CloseUtil.close(bufferOutput);
                	CloseUtil.close(output);
                }
            }
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
        }
    }

    public static void write(String path, String content) throws IOException {
    	int lastIndex = path.lastIndexOf('/');
    	if (lastIndex == -1) {
    		lastIndex = path.lastIndexOf('\\');
    	}
        String newPath = path.substring(0, lastIndex);
        String name = path.substring(lastIndex + 1);
        write(newPath, name, content);
    }
    
    public static void write(String path, String name, String content) throws IOException {
        BufferedWriter writer = null;
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            final File htmlFile = new File(path, name);
            writer = new BufferedWriter(new FileWriter(htmlFile));
            writer.write(content);
            writer.flush();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (final IOException e) {
                }
            }
        }
    }

    public static void write(final File distFile, final InputStream in
            ) throws FileNotFoundException, IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(distFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
        	CloseUtil.close(in);
        	CloseUtil.close(out);
        }
    }

    public static void copyFolder(final File srcPath, final File dstPath) throws IOException {
        final String[] files = srcPath.list();
        for (final String file : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new FileInputStream(new File(srcPath, file));
                out = new FileOutputStream(new File(dstPath, file));
                final byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
            	CloseUtil.close(in);
            	CloseUtil.close(out);
            }
        }
    }
    
    public static void copyFile(final File src, final File dest) throws IOException {
        if (!src.exists()) {
            throw new IOException("The source file does not exist! path: " + src);
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);
            final byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
        	CloseUtil.close(in);
        	CloseUtil.close(out);
        }
    }

    public static void delete(final File path) {
        final String[] files = path.list();
        String parentPath = path.getPath();
        for (final String name : files) {
            final File file = new File(parentPath, name);
            if (file.isDirectory()) {
                delete(file);
            }
            file.delete();
        }
        path.delete();
    }
    
    public static String readFile(InputStream in) {
    	StringBuffer sb = new StringBuffer();
		InputStreamReader rd = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(rd);
		String text = new String();
		try {
			while (text != null) {
					text = br.readLine();
				if (text != null) {
					sb.append(text);
				}
			}
		} catch (IOException e) {
			
		} finally {
			CloseUtil.close(in);
		}
		return sb.toString();
	}
}
