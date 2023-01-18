import { Observable } from 'rxjs/Observable';
import { Injectable, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs/Subject';

// Source: https://stackoverflow.com/a/41989983/6644646
@Injectable({ providedIn: 'root'})
export class ShowChecklistTabService implements OnDestroy {
    // Observable string sources
    private emitChangeSource = new Subject<any>();

    // Observable string streams
    changeEmitted$ = this.emitChangeSource.asObservable();

    // Service message commands
    exibeChecklist(show: boolean) {
        this.emitChangeSource.next(show);
    }

    ngOnDestroy() {
        this.emitChangeSource.unsubscribe();
    }
}
