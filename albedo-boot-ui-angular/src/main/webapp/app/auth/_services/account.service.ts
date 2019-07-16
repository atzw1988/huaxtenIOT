import { Injectable } from '@angular/core'
import { Observable } from 'rxjs/Rx'
import { CTX } from '../../app.constants'
import { HttpClient } from "@angular/common/http";

@Injectable()
export class AccountService {
    constructor(private http: HttpClient) {
    }

    get(): Observable<any> {
        return this.http.get(CTX + '/account').map((res: any) => res.data)
            //     return this.http.get('http://192.168.1.109:8066/api/account').map((res: any) => res.data)
    }


    save(account: any): Observable<any> {
        return this.http.post(CTX + '/account', account)
        // return this.http.post('http://192.168.1.109:8066/api/account', account)
    }

}
