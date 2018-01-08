import { Component } from '@angular/core';

import {MenuItem} from '../../node_modules/primeng/components/common/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  items: MenuItem[];
  
      ngOnInit() {
          this.items = [
            {
                
                icon: 'fa-home',
                routerLink:['/home']                    
            },
              {
                  label: 'Template',
                  items: [{
                          label: 'Create Template', 
                          icon: 'fa-plus',
                          routerLink:['/createTemplate/0'] 
                                                      
                      },
                      {
                        label: 'View All Templates',
                        icon: 'fa-eye',
                        routerLink:['/reportTemplate']    
                      }
                  ]
              },
              {
                  label: 'All Report Tasks',
                  icon: 'fa-file-text-o',
                  routerLink:['/reportTask']                    
              }
          ];
      }
}
