export interface BuyACar {
  id?: number;
  buyDate: Date | string;
  price: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  userId: number;
  carId: number;
  username?: string;
  carName?: string;
  carImage?: string;  // ← أضف

}
