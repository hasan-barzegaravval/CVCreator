import com.mycompany.mavenproject3.Commons.FILES;
import com.mycompany.mavenproject3.Commons.STRINGS;
import com.mycompany.mavenproject3.Definitions.PUBLICATION;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.logging.Formatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;


/*
 * Copyright (C) 2022 Hasan Barzegaravval
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Test class for Common methods including the RISFilereader and String-List conversions.
 * @author  Hasan Barzegaravval
 * @version 1.0
 * @see     junit.jupiter
 */

public class CommonTest {
    static Random rn;
    static Logger log;
    static String ResourcePath;
    /**
     * setup the requirement for the test class.
     * Defining the logger, the random generator and resource path for test file.
     */
    @BeforeAll
    public static void setup(){
        rn=new Random();
        log=Logger.getLogger(CommonTest.class.getName());
        for (var hand:log.getParent().getHandlers()) log.getParent().removeHandler(hand);
        ConsoleHandler handler=new ConsoleHandler();
        handler.setFormatter(new Formatter(){
            @Override
            public String format(LogRecord lr) {
                StringBuilder sb = new StringBuilder();
                sb.append(formatMessage(lr)).append(System.getProperty("line.separator"));
                return sb.toString();
            }
            
        });
        log.addHandler(handler);
        ResourcePath=System.getProperty("user.dir")+"\\src\\main\\resources\\com\\mycompany\\TestResources\\";   
    }
    /**
     * This Test is a random generated repeating test for string to list and list to string 
     * methods.
     * @param TI is the TestInfo object containing the test information.
     * @param RI is the RepetitionInfo object containing each repetition information.
     * @see Tesinfo,RepetitionInfo,RepeatedTest
     */
    @RepeatedTest(value=10,name=RepeatedTest.DISPLAY_NAME_PLACEHOLDER)
    @DisplayName("Testing string to list and reverse operation")
    public void  teststringlist(TestInfo TI,RepetitionInfo RI){
                if (RI.getCurrentRepetition()==1){log.info(TI.getDisplayName());} 
                log.info(String.format("Repeatation %d of %d",RI.getCurrentRepetition(),RI.getCurrentRepetition()));                
                int size=rn.nextInt(10);
                assumingThat(size>0,()->{                    
                    List<String> A=new ArrayList();
                    while (A.size()<size)A.add(UUID.randomUUID().toString());                   
                    String B=STRINGS.ListToString(A,'\n');
                    List<String> C=STRINGS.StringToList(B, "\n");
                    assertEquals(A,C);
                });            
    }
    /**
     * This is a Tester for the RIS file reader. 
     * The concept of the test is to evaluate number of citation entries
     * and authors for a selected citation index.     * 
     * @param testcase  index of the test case argument
     * @param fname     RIS file name
     * @param NC        Number of citation entries in file
     * @param index     index of selected citation for authors check
     * @param AN        Number of authors in the selected citation.
     * @see ParameterizedTest,MethodSource
     */
    @ParameterizedTest
    @MethodSource("RISTestProvider")
    public void RISTest(int testcase,String fname,int NC,int index,int AN){
        if (testcase==1) log.info("---Test RIS Reader function.----");
        
        try{
            List<PUBLICATION> LP=FILES.RISREADER(ResourcePath+fname);
            log.log(Level.INFO, "----------\nTest Case={0}\nFile Name={1}", new Object[]{String.valueOf(testcase), fname});
            assertEquals(LP.size(),NC);
            log.info("Number of Citation was correct");
            assertEquals(LP.get(index).getAuthors().size(),AN);
            log.info("Number of authors was correct");
            
        }catch(IOException e){
            log.log(Level.INFO, "RISTest is aborted for file IO exception: {0}", e.getMessage());
        }
            log.log(Level.INFO,"End of RISTest for testcase {0}\n -----------",String.valueOf(testcase));
    }
    /**
     * The Argument provider for the parameterized test  RISTest funciton.
     * @return Stream of arguments for test RISTest
     */
    public static Stream<Arguments> RISTestProvider(){
        /** 
         * arguments elements are :
         * int    Test case number
         * String File name
         * int    Number of entries (citations in the file)
         * int    Number of authors for a selected entry
         * int    index of selected entry
         **/
        return Stream.of(
                arguments(1,"citations.ris",19,5,4)
        );
    }
}
