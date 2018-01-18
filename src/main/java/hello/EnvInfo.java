package hello;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Process;
import java.lang.Runtime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EnvInfo {

  private Map<String, String> envInfo = new HashMap<String,String>();

  public EnvInfo(String filter) throws IOException {
    this.envInfo = mapEnvInfo(filter);
  }

  public Map<String, String> getEnv() {
    return envInfo;
  }final

  public static Map<String,String> mapEnvInfo(String filter) throws IOException {
      Process proc = Runtime.getRuntime().exec("env");
      Map<String, String> ret = new HashMap<String,String>();
            try (InputStream stream = proc.getInputStream()) {
          try (Scanner s = new Scanner(stream).useDelimiter("\\n")) {
        	  while (s.hasNext())
        	  {
                  String val =  s.next();
                  String[] nameVal = val.split("=");
                  if (filter.equalsIgnoreCase("*"))
                	  ret.put(nameVal[0],nameVal.length > 1 ? nameVal[1] : "");
                  else
                  {
                	  if (nameVal[0].startsWith(filter))
                	  {
                    	  ret.put(nameVal[0],nameVal.length > 1 ? nameVal[1] : "");
                	  }
                  }
                  
        	  }
          } 
      } 
      return ret;
  }
}
