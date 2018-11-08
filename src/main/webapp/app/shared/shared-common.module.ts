import { NgModule } from '@angular/core';

import { PropsyTestSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PropsyTestSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PropsyTestSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PropsyTestSharedCommonModule {}
