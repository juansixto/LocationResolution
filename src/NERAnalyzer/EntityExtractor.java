package NERAnalyzer;

import java.util.ArrayList;
import java.util.List;
 
public class EntityExtractor {
        private int numberOfEntities;
       
        public List<String> getEntities(String text, String entity) {
                this.setNumberOfEntities(0);
                List<String> entities = new ArrayList<String>();
               
                text = text.replaceAll("<\\/", "</");
 
                entity = entity.toUpperCase();
                int ini = 0;
                int end = 0;
 
                while(ini != -1) {
                        ini = text.indexOf("<" + entity + ">", end);
                        end = text.indexOf("</" + entity + ">", end + 1);
                        if(ini != -1 && end != -1) {
                                entities.add(text.substring(ini + entity.length() + 2, end));
                                this.incNumberOfEntities();
                        }
                }
               
                return entities;
        }
 
        /**
         * @return the numberOfEntities
         */
        public int getNumberOfEntities() {
                return numberOfEntities;
        }
 
        /**
         * @param numberOfEntities the numberOfEntities to set
         */
        private void setNumberOfEntities(int numberOfEntities) {
                this.numberOfEntities = numberOfEntities;
        }
       
        private void incNumberOfEntities() {
                this.numberOfEntities++;
        }
}