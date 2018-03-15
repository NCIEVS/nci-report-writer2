import { LookupvaluesTemplaterowService } from './../../service/lookupvalues-templaterow.service';
import { TemplateRow } from './../../model/template-row';
import { Template } from './../../model/template';
import { LookupvaluesTemplateService } from './../../service/lookupvalues-template.service';
import { ReportTemplateService } from './../../service/report-template.service';
import { Component, OnInit } from '@angular/core';
import { Lookup } from './../../model/lookup';

import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-template',
  templateUrl: './create-template.component.html',
  styleUrls: ['./create-template.component.css']
})
export class CreateTemplateComponent implements OnInit {
  templateId: number;
  title: string;
  lookupNone: Lookup;
  closeResult: string;
  types: Lookup[];
  associations: Lookup[];
  statuses: Lookup[];

  displays: Lookup[];
  propertyTypes: Lookup[];
  propertyTargets: Lookup[];
  sources: Lookup[];
  groups: Lookup[];
  subsources: Lookup[];

  selectedType: Lookup;
  selectedAssociation: Lookup;
  selectedStatus: Lookup;

  template = new Template();

  clonetemplate : Template;

  templateRowUI = new TemplateRow();
  templateRowAdd = new TemplateRow();
  selectedTemplateRow: TemplateRow;


  templateCreated: boolean;
  templateButtonName: string;

  templateRows: TemplateRow[];


  displayMessage: string;

  staticAlertClosed = false;


  templateRowcols: any[];

  displayAddRow: boolean = false;

  showAddRow() {
    this.displayAddRow = true;
  }

arraymove(arr, fromIndex, toIndex) {
    var element = arr[fromIndex];
    arr.splice(fromIndex, 1);
    arr.splice(toIndex, 0, element);
}


 
  moveRowUp(){
   if (this.selectedTemplateRow != undefined){
      console.log("selected row - " + JSON.stringify(this.selectedTemplateRow));
      var fromIndex = this.templateRows.indexOf(this.selectedTemplateRow);
      //console.log("index - " + fromIndex);
      if (fromIndex == 0){
        return;
      }
      //console.log("selected row - " + JSON.stringify(this.templateRows));
      this.arraymove(this.templateRows,fromIndex,fromIndex-1);
    // console.log("selected row - " + JSON.stringify(this.templateRows));
    } else{
      alert("Select a template row to move.");
    }
  }

  moveRowDown(){
    if (this.selectedTemplateRow != undefined){
      console.log("selected row - " + JSON.stringify(this.selectedTemplateRow));
      var fromIndex = this.templateRows.indexOf(this.selectedTemplateRow);
      //console.log("index - " + fromIndex);
      if (fromIndex == (this.templateRows.length -1)){
        return;
      }
      //console.log("selected row - " + JSON.stringify(this.templateRows));
      this.arraymove(this.templateRows,fromIndex,fromIndex+1);
    // console.log("selected row - " + JSON.stringify(this.templateRows));
    } else {
      alert("Select a template row to move.");
    }
  }

  deleteTemplateRow(templateRow) {
    console.log("In deleteTemplateRow - templateRow" + JSON.stringify(templateRow));
    console.log("In deleteTemplateRow - templateRows" + JSON.stringify(this.templateRows));
    this.templateRows = this.templateRows.filter(templateRowIn => templateRowIn.columnNumber != templateRow.columnNumber);
    console.log("In deleteTemplateRow - templateRows" + JSON.stringify(this.templateRows));
  }

  onSubmitAddRow() {
    this.displayAddRow = false;

    console.log("templateRowUI - " + JSON.stringify(this.templateRowUI));

    this.templateRowAdd = new TemplateRow();
    if (this.templateRows == undefined || this.templateRows == null || this.templateRows.length == 0) {
      this.templateRowAdd.columnNumber = 1;
    } else {
      this.templateRowAdd.columnNumber = this.templateRows[this.templateRows.length - 1].columnNumber + 1;
    }


    this.templateRowAdd.label = this.templateRowUI.label;
    this.templateRowAdd.display = this.templateRowUI.display;
    this.templateRowAdd.propertyType = this.templateRowUI.propertyType;
    this.templateRowAdd.property = this.templateRowUI.property;
    if (this.templateRowUI.source == undefined || this.templateRowUI.source == null) {
      this.templateRowAdd.source = null;
    } else {
      this.templateRowAdd.source = this.templateRowUI.source;
    }
    if (this.templateRowUI.group == undefined || this.templateRowUI.group == null) {
      this.templateRowAdd.group = null;
    } else {
      this.templateRowAdd.group = this.templateRowUI.group;
    }
    if (this.templateRowUI.subsource == undefined || this.templateRowUI.subsource == null) {
      this.templateRowAdd.subsource = null;
    } else {
      this.templateRowAdd.subsource = this.templateRowUI.subsource;
    }

    console.log("templateRowAdd - " + JSON.stringify(this.templateRowAdd));

    if (this.templateRows == undefined || this.templateRows == null || this.templateRows.length == 0) {
      this.templateRows = [this.templateRowAdd];
    } else {
      this.templateRows = [...this.templateRows, this.templateRowAdd];
    }
    console.log("templateRows" + JSON.stringify(this.templateRows));
  }

  cancelAddRow(): void {

    this.displayAddRow = false;

  }




  constructor(private lookupvaluesTemplateService: LookupvaluesTemplateService,
    private modalService: NgbModal,
    private lookupvaluesTemplaterowService: LookupvaluesTemplaterowService,
    private reportTemplateService: ReportTemplateService, private route: ActivatedRoute) {

    this.templateId = route.snapshot.params['id'];
    console.log("templateId -" + this.templateId);

    /* if (this.templateId != null || this.templateId!= undefined || this.templateId != 0){
         this.reportTemplateService.getReportTemplate(this.templateId).subscribe(template => {
         this.template = template;
         this.templateRows = template.columns;
         console.log("after getReportTemplate ----- " + JSON.stringify(this.template));
         console.log("after getReportTemplate ----- " + JSON.stringify(this.templateRows));
         this.templateCreated = true;
         this.templateButtonName = "Save Changes";
        
        
       });
     }*/


  }

  ngOnInit() {
    console.log("In ngInit***")

    this.lookupNone = new Lookup();
    this.lookupNone.label = "None";
    this.lookupNone.value = "None";

    this.getTypes();
    this.getAssociations();
    this.getStatuses();
    this.getDisplays();
    this.getPropertyTypes();
    this.getPropertyTargets();

   
    

    this.getSources();
    this.getGroups();
    this.getSubsources();


    //this.sources.push(this.lookupNone);
    //this.groups.push(this.lookupNone);
    //this.subsources.push(this.lookupNone);

    if (this.templateId != null && this.templateId != undefined && this.templateId != 0) {
      this.templateCreated = true;
      this.templateButtonName = "Save Changes";
      this.title = "Edit Template";
      this.staticAlertClosed = true;
      this.reportTemplateService.getReportTemplate(this.templateId).subscribe(template => {
        this.template = template;
        this.templateRows = template.columns;
        console.log("after getReportTemplate ----- " + JSON.stringify(this.template));
        console.log("after getReportTemplate ----- " + JSON.stringify(this.templateRows));
      });
    } else {
      this.title = "Create Template";
      this.templateCreated = false;
      this.templateButtonName = "Create Template";
      this.template = new Template();
      this.templateRows = [];
    }

    this.templateRowcols = [
      { field: 'label', header: 'Label', editable: true },
      { field: 'display', header: 'Display', editable: true },
      { field: 'propertyType', header: 'Property Type', editable: true },
      { field: 'property', header: 'Property', editable: true },
      { field: 'source', header: 'Source', editable: true },
      { field: 'group', header: 'Group', editable: true },
      { field: 'subsource', header: 'Subsource', editable: true }

    ];
  }







  getTypes(): void {
    this.lookupvaluesTemplateService.getTypes().subscribe(types => this.types = types);
  }

  getAssociations(): void {
    this.lookupvaluesTemplateService.getAssociations().subscribe(associations => this.associations = associations);
  }

  getStatuses(): void {
    this.lookupvaluesTemplateService.getStatuses().subscribe(statuses => this.statuses = statuses);
  }


  getDisplays(): void {
    this.lookupvaluesTemplaterowService.getDisplays().subscribe(displays => this.displays = displays);
  }

  getPropertyTypes(): void {
    this.lookupvaluesTemplaterowService.getPropertyTypes().subscribe(propertyTypes => {this.propertyTypes = propertyTypes;});
  }

  getPropertyTargets(): void {
    this.lookupvaluesTemplaterowService.getPropertyTargets().subscribe(propertyTargets => {this.propertyTargets = propertyTargets;});
  }



  getSources(): void {
    this.lookupvaluesTemplaterowService.getSources().subscribe(sources => { this.sources = sources; this.sources.push(this.lookupNone) });
  }

  getGroups(): void {
    this.lookupvaluesTemplaterowService.getGroups().subscribe(groups => { this.groups = groups; this.groups.push(this.lookupNone) });
  }

  getSubsources(): void {
    this.lookupvaluesTemplaterowService.getSubsources().subscribe(subsources => { this.subsources = subsources; this.subsources.push(this.lookupNone) });
  }

  clearTemplateRows() {
    this.templateRows = [];
  }

  onSubmitTemplate(value) {
    console.log("** - *** " + value)

    let count = 1;
    for (let templateRow of this.templateRows) {
      templateRow.columnNumber = count;
      templateRow.id = null;
      if (templateRow.source == 'None')
      templateRow.source = null;
      if (templateRow.group == 'None')
      templateRow.group = null;
      if (templateRow.subsource == 'None')
      templateRow.subsource = null;
      count++;
    }
    this.template.columns = this.templateRows;
    console.log("before onSubmitTemplate ----- " + JSON.stringify(this.template));

    if (!this.templateCreated) {

      this.reportTemplateService.addReportTemplate(this.template).subscribe(template => {
        console.log("Returned from addReportTemplate " +  JSON.stringify(template));
        this.template = template;
        this.templateRows = template.columns;
        console.log("after onSubmitTemplate addReportTemplate----- " + JSON.stringify(this.template));
        console.log("after onSubmitTemplate addReportTemplate----- " + JSON.stringify(this.templateRows));
        this.templateCreated = true;
        this.templateButtonName = "Save Changes";
        this.displayMessage = " Template has been created. You can continue to edit the template or click on Cancel to exit.";
        setTimeout(() => { this.staticAlertClosed = true; console.log("setting staticAlertClosed to true") }, 8000);
      });

    } else {
      this.reportTemplateService.saveReportTemplate(this.template).subscribe(template => {
        console.log("Returned from addReportTemplate " +  JSON.stringify(template));
        this.template = template;
        this.templateRows = template.columns;
        console.log("after onSubmitTemplate saveReportTemplate----- " + JSON.stringify(this.template));
        console.log("after onSubmitTemplate saveReportTemplate ----- " + JSON.stringify(this.templateRows));
        this.staticAlertClosed = false;
        this.displayMessage = " The changes made to the Template have been saved. You can continue to edit the template or click on Cancel to exit.";
        setTimeout(() => { this.staticAlertClosed = true; console.log("setting staticAlertClosed to true") }, 8000);
      });



    }

  }


  open(content) {
    this.modalService.open(content).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.templateRowUI = new TemplateRow();
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {

    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }


  }


}
