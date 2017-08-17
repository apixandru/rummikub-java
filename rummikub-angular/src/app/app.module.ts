import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AppCardSlot } from "./app.cardslot";

import { DndModule } from 'ng2-dnd';

@NgModule({
  declarations: [
    AppComponent,
    AppCardSlot
  ],
  imports: [
    DndModule.forRoot(),
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
