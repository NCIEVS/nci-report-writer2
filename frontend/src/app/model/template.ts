import { Lookup } from './lookup';
import { TemplateRow } from './template-row';

export class Template {
    
      constructor(  
        public id?:number,    
        public name?: string ,
        public type?:string,
        public association?:string,
        public rootConceptCode?:string,
        public level?:number,
        public status?:string, 
        public sortColumn?:number,
        public columns?: TemplateRow[]
       
      ) {  }
    
    }