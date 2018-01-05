import { TableHeader } from './tableHeader';
import { ReportData } from './reportData';

export class ReportTaskOutput {
    
      constructor(  
        public header?:TableHeader[],    
        public data?:ReportData[]       
      ) {  }
    

     
    }