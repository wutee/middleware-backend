import { NgModule } from '@angular/core';

import { PropsyBackendSampleSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PropsyBackendSampleSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PropsyBackendSampleSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PropsyBackendSampleSharedCommonModule {}
