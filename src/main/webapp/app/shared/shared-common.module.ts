import { NgModule } from '@angular/core';

import { PropsyBackendSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PropsyBackendSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PropsyBackendSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PropsyBackendSharedCommonModule {}
