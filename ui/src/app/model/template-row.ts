import { Lookup } from "./lookup";

export class TemplateRow {
    
      constructor(  
        public id?:number,  
        public columnNumber?:number,   
        public label?: string ,
        public display?:string,
        public propertyType?:string,
        public property?:string,
        public source?:string,
        public group?:string, 
        public subsource?:string, 
      ) {  }
    
    }




    export class TemplateRowUI {
      
        constructor(   
          public columnNumber?:number,   
          public label?: string ,
          public display?:Lookup,
          public propertyType?:Lookup,
          public propertyTarget?:Lookup,
          public source?:Lookup,
          public group?:Lookup, 
          public subsource?:Lookup, 
        ) {  }
      
      }