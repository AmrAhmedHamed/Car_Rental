export interface BookACar {
  id?: number;
  fromDate: Date | string;
  toDate: Date | string;
  days: number;
  price: number;
  bookCarStatus: 'PENDING' | 'APPROVED' | 'REJECTED';
  userId: number;
  carId: number;
  username?: string;   // ← make optional (not required in request)
  carName?: string;
  carImage?: string;
  paymentIntentId?: string;


}
