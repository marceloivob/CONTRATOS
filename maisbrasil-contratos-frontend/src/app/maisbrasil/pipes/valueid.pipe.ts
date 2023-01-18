import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'valueId'
  })
  export class ValueIdPipe implements PipeTransform {
    transform(values: any[]): string[] {
      if (values) {
        return values.map(v => v.id);
      } else {
        return []
      } 
    }
  }