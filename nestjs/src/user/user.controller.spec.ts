import { Test, TestingModule } from '@nestjs/testing';
import { UserController } from './user.controller';
import { UserService } from './user.service';
import { User } from './entities/user.entity';
import { CacheModule } from '../cache/cache.module';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { UserNotFoundException } from './exceptions/user-not-found.exception';

const name = 'john';
const age = 12;

const user = new User(name, age);

const users = [user, new User('leonard', 12), new User('sheldon', 12)];

describe('UserController', () => {
  let controller: UserController;
  let service: UserService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [CacheModule],
      controllers: [UserController],
      providers: [
        {
          provide: UserService,
          useValue: {
            create: jest
              .fn()
              .mockImplementation((createUserDto: CreateUserDto) =>
                Promise.resolve({ id: 1, ...createUserDto }),
              ),
            findAll: jest.fn().mockResolvedValue(users),
            findOne: jest
              .fn()
              .mockImplementation((id: string) =>
                Promise.resolve({ id, ...user }),
              ),
            update: jest
              .fn()
              .mockImplementation((id: string, updateUserDto: UpdateUserDto) =>
                Promise.resolve({ id, ...updateUserDto }),
              ),
            remove: jest.fn().mockResolvedValue(true),
          },
        },
      ],
    }).compile();

    controller = module.get<UserController>(UserController);
    service = module.get<UserService>(UserService);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
  describe('create', () => {
    it('should create a new user', async () => {
      const userDto: CreateUserDto = {
        name: name,
        age: age,
      };
      await expect(controller.create(userDto)).resolves.toEqual({
        id: 1,
        ...userDto,
      });
    });
  });
  describe('findAll', () => {
    it('should get an array of users', async () => {
      await expect(controller.findAll()).resolves.toEqual(users);
    });
  });
  describe('findOne', () => {
    it('should get a single user', async () => {
      await expect(controller.findOne(1)).resolves.toEqual({
        id: 1,
        ...user,
      });
    });
  });
  describe('update', () => {
    it('should update an user', async () => {
      const updateUserDto: UpdateUserDto = {
        name: name,
        age: age,
      };
      await expect(controller.update(1, updateUserDto)).resolves.toEqual({
        id: 1,
        ...updateUserDto,
      });
    });
  });
  describe('remove', () => {
    it('should return that it deleted an user', async () => {
      await expect(controller.remove(1)).resolves.toBeTruthy();
    });
    it('should return that it did not delete an user', async () => {
      const deleteSpy = jest.spyOn(service, 'remove').mockRejectedValue(() => {
        throw new UserNotFoundException(2);
      });
      await expect(controller.remove(2)).rejects.toThrow(
        'User with id 2 not found',
      );
      expect(deleteSpy).toBeCalledWith(2);
    });
  });
});
