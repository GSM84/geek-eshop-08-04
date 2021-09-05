export class Pageble {

  constructor(public pageSize: number,
              public pageNumber: number,
              public offset: number,
              public unpaged: boolean,
              public paged: boolean) {
  }
}
